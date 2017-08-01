package com.playtech.ptargame3.common.io;

import com.playtech.ptargame3.common.session.Session;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NioServerConnector implements Runnable {

    private static final Logger logger = Logger.getLogger(NioServerConnector.class.getName());

    private static final int READ_BUFFER_SIZE = 16384;

    private final ConnectionFactory connectionFactory;

    private Selector selector;
    private ByteBuffer buffer = ByteBuffer.allocateDirect(READ_BUFFER_SIZE);
    private volatile boolean stop = false;

    private final Queue<PendingConnection> pendingConnects = new ConcurrentLinkedQueue<>();

    public NioServerConnector(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private void initialize() {
        if (this.selector != null) throw new IllegalStateException("Connector already initialized.");
        try{
            selector = Selector.open();
        }catch (IOException noHandles) {
            throw new ExceptionInInitializerError(noHandles);
        }
    }

    public Runnable start() {
        initialize();
        return this;
    }

    public void stop() {
        if (!stop) {
            logger.log(Level.INFO, "Stop triggered for connector.");
            this.stop = true;
        }
    }

    @Override public void run() {
        try {
            logger.info("Connector starting.");

            // process connections
            while(this.selector.isOpen() && !stop) {
                processKeys();
                processPing();
                processConnects();
            }

        } catch(IOException e) {
            logger.log(Level.INFO, "IOException, Connector terminating. Stack trace:", e);
        } finally {
            // closeEverything connections and selector
            closeEverything();
        }
    }

    public Session connect(InetSocketAddress address) throws IOException {
        ConnectionHandler connection = this.connectionFactory.createConnection();
        pendingConnects.offer(new PendingConnection(connection, address));
        return connection.getSession();
    }

    private void processPing() {
        long time = System.currentTimeMillis();
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
                    connection.ping(time);
                }
            }
        }
    }

    private void processKeys() throws IOException {
        this.selector.select(100);
        Iterator<SelectionKey> i=this.selector.selectedKeys().iterator();
        while(i.hasNext()) {
            SelectionKey key = i.next();
            i.remove();

            ConnectionHandler connection = ((ConnectionHandler)key.attachment());
            if (connection != null) {
                connection.processKey(this.buffer);
            }
        }
    }

    private void processConnects() throws IOException {
        PendingConnection pc;
        while ((pc = this.pendingConnects.poll()) != null) {
            pc.connection.initializeConnect(this.selector, pc.address);
        }
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
            this.selector.close();
        } catch (Exception ignore) {}
        logger.log(Level.INFO, "Connector stopped.");
    }

    private static class PendingConnection {
        private final ConnectionHandler connection;
        private final InetSocketAddress address;

        public PendingConnection(ConnectionHandler connection, InetSocketAddress address) {
            this.connection = connection;
            this.address = address;
        }
    }

}
