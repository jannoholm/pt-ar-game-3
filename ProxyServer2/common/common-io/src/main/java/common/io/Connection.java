package common.io;

import common.session.Session;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface Connection {

    void close();

    void write(List<ByteBuffer> message);

    int getConnectionId();

    Session getSession();

}
