package common.io;



import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class NioServerListener implements Runnable {

    private static final Logger logger = Logger.getLogger(NioServerListener.class.getName());

    private static final int READ_BUFFER_SIZE = 16384;

    private final ConnectionFactory connectionFactory;

    private final int port;
    private ServerSocketChannel ssc;
    private Selector selector;
    private ByteBuffer buffer = ByteBuffer.allocateDirect(READ_BUFFER_SIZE);
    private volatile boolean stop = false;

    public NioServerListener(ConnectionFactory connectionFactory, int port) {
        this.connectionFactory = connectionFactory;
        this.port = port;
    }

    public void initialize() throws IOException {
        if (this.selector != null) throw new IllegalStateException("Listener already initialized.");
        this.selector = Selector.open();
        this.ssc = ServerSocketChannel.open();
        this.ssc.socket().bind(new InetSocketAddress(port));
        this.ssc.configureBlocking(false);
        this.ssc.register(this.selector, SelectionKey.OP_ACCEPT, "ServerSocket");
    }

    public Runnable start() throws IOException {
        initialize();
        return this;
    }

    public void stop() {
        if (!stop) {
            logger.log(Level.INFO, "Stopping scheduled for server at " +this.port);
            this.stop = true;
        }
    }

    @Override public void run() {
        try {
            logger.info("Server starting on port " + this.port);

            while(this.ssc.isOpen() && !stop) {
                processKeys();
                processPing();
            }
        } catch(IOException e) {
            logger.log(Level.INFO, "IOException, server of port " +this.port+ " terminating. Stack trace:", e);
        } finally {
            closeEverything();
        }
    }

    private void processPing() {
        long start = System.currentTimeMillis();
        for (SelectionKey key : this.selector.keys() ) {
            if (key.attachment() instanceof ConnectionHandler){
                ConnectionHandler connection = ((ConnectionHandler)key.attachment());
                if (connection == null) {
                    try {
                        key.cancel();
                        key.channel().close();
                        logger.info("Closing not properly initialized connection." + connection);
                    } catch (Exception ignored) {}
                } else {
                    connection.ping(start);
                }
            }
        }
        long end = System.currentTimeMillis();
        logger.log(Level.FINER, ()->"processPing.time " + (end-start) + "ms");
    }

    private void processKeys() throws IOException {
        this.selector.select(100);
        long start = System.currentTimeMillis();
        Iterator<SelectionKey> i=this.selector.selectedKeys().iterator();
        while(i.hasNext()) {
            SelectionKey key = i.next();
            i.remove();

            if(key.isAcceptable()) {
                ConnectionHandler connection = this.connectionFactory.createConnection();
                connection.initializeAccept(this.selector, key);
            } else {
                ConnectionHandler connection = ((ConnectionHandler)key.attachment());
                if (connection != null) {
                    connection.processKey(this.buffer);
                }
            }
        }
        long end = System.currentTimeMillis();
        logger.log(Level.FINER, ()->"processKeys.time " + (end-start) + "ms");
    }

    private void closeEverything() {
        for (SelectionKey key : this.selector.keys() ) {
            if (key.attachment() instanceof ConnectionHandler){
                ConnectionHandler connection = ((ConnectionHandler)key.attachment());
                if (connection == null) {
                    try {
                        key.cancel();
                        key.channel().close();
                    } catch (Exception ignored) {}
                } else {
                    connection.close();
                }
            }
        }
        try {
            this.ssc.close();
        } catch (IOException ignore) {}
        try {
            this.selector.close();
        } catch (IOException ignore) {}
        logger.log(Level.INFO, "Server stopped server at " +this.port);
    }

}