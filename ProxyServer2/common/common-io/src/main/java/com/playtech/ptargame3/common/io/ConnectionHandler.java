package com.playtech.ptargame3.common.io;

import com.playtech.ptargame3.common.io.separator.Encoder;
import com.playtech.ptargame3.common.io.separator.Decoder;
import com.playtech.ptargame3.common.session.Session;
import com.playtech.ptargame3.common.util.HexUtil;
import com.playtech.ptargame3.common.util.IdGenerator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("MagicConstant")
public final class ConnectionHandler implements Connection {

    private static final Logger logger = Logger.getLogger(ConnectionHandler.class.getName());

    private static final IdGenerator idGenerator = new IdGenerator(1000);

    private final int connectionId = idGenerator.generateId();

    private final Encoder encoder;
    private final Decoder decoder;
    private Session session;

    private final ByteBuffer mainMessageBuffer; // 4k direct buffer
    private ByteBuffer largeMessageBuffer;      // 12k on demand

    private SelectionKey key;
    private SocketChannel socketChannel;
    private final Queue<ByteBuffer> pendingWrites = new ConcurrentLinkedQueue<>();

    public ConnectionHandler(Encoder encoder, Decoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.mainMessageBuffer = ByteBuffer.allocateDirect(4096).order(ByteOrder.LITTLE_ENDIAN);
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private int getInitialOps() {
        int ops = SelectionKey.OP_READ;
        if (!this.pendingWrites.isEmpty()) {
            ops = ops | SelectionKey.OP_WRITE;
        }
        return ops;
    }

    /**
     * Initialize connection for processing.
     * @param selector selector, where connection is registered
     * @param key      selection key to get the new connection
     */
    synchronized void initializeAccept(Selector selector, SelectionKey key) {
        try {
            this.socketChannel = ((ServerSocketChannel) key.channel()).accept();
            this.socketChannel.configureBlocking(false);
            this.key = this.socketChannel.register(selector, getInitialOps(), this);
            logger.info(()->String.format(" %6s accepted connection from: %s", connectionId, this.socketChannel.socket().getInetAddress().toString()));
        } catch (IOException e) {
            logger.log(Level.WARNING, "Unable to create connection.", e);
            processClose();
        }
    }

    synchronized void initializeConnect(Selector selector, InetSocketAddress address) {
        try {
            this.socketChannel = selector.provider().openSocketChannel();
            this.socketChannel.configureBlocking(false);
            this.socketChannel.connect(address);
            int ops = this.socketChannel.isConnectionPending() ? SelectionKey.OP_CONNECT : getInitialOps();
            this.key = this.socketChannel.register(selector, ops, this);
            logger.info(()->String.format(" %6s opening connection to: %s", connectionId, address.toString()));
        } catch (IOException e) {
            logger.log(Level.WARNING, "Unable to create connection.", e);
            processClose();
        }
    }

    /**
     * Process channel for read, writes, etc.
     * @param ioBuffer shared buffer to do io operations
     */
    synchronized void processKey(ByteBuffer ioBuffer) {
        try {
            if (!this.key.isValid()) processClose();
            if (this.key.isValid() && this.key.isConnectable()) processConnect();
            if (this.key.isValid() && this.key.isReadable()) processRead(ioBuffer);
            if (this.key.isValid() && this.key.isWritable()) processWrite();
        } catch (Exception e) {
            logger.log(Level.WARNING, String.format(" %6s Unable to process connection.", connectionId), e);
            processClose();
        }
    }

    /**
     * Called periodically to send ping messages and remove inactive connections
     * @param time time of the ping
     */
    void ping(long time) {
        this.session.ping(time);
    }

    @Override
    public synchronized void close() {
        processClose();
    }

    /**
     * Used to send data through this connection
     * @param message message to write
     */
    public synchronized void write(List<ByteBuffer> message) {
        if (this.key != null && !this.key.isValid()){
            logger.info(String.format(" %6s dropping message. Connection closed.", connectionId));
            return;
        }
        // try optimistic write
        try {
            List<ByteBuffer> encoded = this.encoder.encode(message);
            for (ByteBuffer buffer : encoded) {
                if (this.pendingWrites.isEmpty() && this.socketChannel != null && this.socketChannel.isConnected()) {
                    if (logger.isLoggable(Level.FINER))
                        logger.finer(String.format(" %6s bytes written: %s", connectionId, HexUtil.toHex(buffer.slice())));
                    this.socketChannel.write(buffer);
                }
                if (buffer.hasRemaining()) {
                    this.pendingWrites.offer(copy(buffer));
                    if (this.key != null) {
                        this.key.interestOps(this.key.interestOps() | SelectionKey.OP_WRITE);
                    }
                }
            }
        } catch (IOException e) {
            logger.log(Level.INFO, String.format(" %6s Exception while writing.", connectionId), e);
            processClose();
        }
    }

    @Override
    public int getConnectionId() {
        return this.connectionId;
    }

    public Session getSession() {
        return this.session;
    }

    private void processConnect() throws IOException {
        if (!this.socketChannel.finishConnect()){
            processClose();
            return;
        }

        if (!this.socketChannel.isConnected()){
            processClose();
            return;
        }

        this.key.interestOps(getInitialOps() | key.interestOps() & ~SelectionKey.OP_CONNECT);//put read, remove connect
    }

    private void processWrite() throws IOException {
        // optimistic plan to write all data
        this.key.interestOps(this.key.interestOps() & ~SelectionKey.OP_WRITE);

        ByteBuffer out;
        while ((out = this.pendingWrites.peek()) != null) {
            // buffer to stream
            if (logger.isLoggable(Level.FINER))
                logger.finer(String.format(" %6s bytes written: %s", connectionId, HexUtil.toHex(out.slice())));
            this.socketChannel.write(out);
            if (out.hasRemaining()) {
                // unable to write. wait for next turn
                this.key.interestOps(this.key.interestOps() | SelectionKey.OP_WRITE);
                break;
            } else {
                // remove buffer and continue with next
                this.pendingWrites.poll();
            }
        }
    }

    private void processRead(ByteBuffer ioBuffer) throws IOException {
        ioBuffer.clear();
        int status;
        while( (status=this.socketChannel.read(ioBuffer)) > 0 ) {
            ioBuffer.flip();
            if (logger.isLoggable(Level.FINER))
                logger.finer(String.format(" %6s bytes read: %s", connectionId, HexUtil.toHex(ioBuffer.slice())));

            while (ioBuffer.hasRemaining()) {
                // decode data
                boolean found = decoder.decode(ioBuffer, mainMessageBuffer);
                if (!found && ioBuffer.hasRemaining()) {
                    if (largeMessageBuffer == null) {
                        largeMessageBuffer = ByteBuffer.allocate(Math.min(12288, ioBuffer.remaining())).order(mainMessageBuffer.order());
                    }
                    found = decoder.decode(ioBuffer, largeMessageBuffer);
                }
                if (!found && ioBuffer.hasRemaining()) {
                    throw new IOException("Unable to fit message to message buffer");
                }

                if (found) {
                    // prepare buffers for passing
                    mainMessageBuffer.flip();
                    List<ByteBuffer> message = new ArrayList<>(2);
                    message.add(mainMessageBuffer);
                    if (largeMessageBuffer != null) {
                        largeMessageBuffer.flip();
                        message.add(largeMessageBuffer);
                    }

                    // pass message data
                    this.session.processMessage(message);

                    // clean message buffers
                    mainMessageBuffer.clear();
                    largeMessageBuffer = null;
                }
            }
            ioBuffer.clear();
        }
        if (status < 0) {
            processClose();
        }
    }

    private void processClose() {
        this.key.cancel();
        this.pendingWrites.clear();
        try{
            if (this.socketChannel.isOpen())
                this.socketChannel.close();
        } catch (IOException ignored) {}
        mainMessageBuffer.clear();
        largeMessageBuffer=null;
        session.cleanup();
        logger.info(String.format(" %6s Connection closed.", connectionId));
    }

    private static ByteBuffer copy(ByteBuffer b){
        return (ByteBuffer) ByteBuffer.allocate(b.remaining()).order(b.order()).put(b).flip();
    }
}
