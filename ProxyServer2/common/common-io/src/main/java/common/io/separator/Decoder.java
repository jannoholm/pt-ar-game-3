package common.io.separator;

import java.nio.ByteBuffer;

public interface Decoder {

    boolean decode(ByteBuffer src, ByteBuffer dst);

}
