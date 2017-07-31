package common.message;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public interface Message {

    MessageHeader getHeader();

    void parse(ByteBuffer messageData);

    void format(ByteBuffer messageData);

}
