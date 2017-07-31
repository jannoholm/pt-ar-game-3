package common.io.separator;

import java.nio.ByteBuffer;

public final class LengthDecoder implements Decoder {

    private int maxLength=16384;

    private int lastLength = 0;
    private int readByteCount = 0;

    public LengthDecoder(int maxLength) {
        this.maxLength = maxLength;
    }

    public boolean decode(ByteBuffer src, ByteBuffer dst) {
        if (readByteCount == 0 && src.remaining() > 3) {
            lastLength = src.getInt();
            readByteCount = 4;
        }
        while (src.hasRemaining()) {
            if (readByteCount < 4) {// first four bytes that are read contain
                // the length
                int in = src.get();
                readByteCount++;
                lastLength |= (in & 0xff) << 8 * (4 - readByteCount);
            } else {
                // copy
                int len = Math.min(dst.remaining(), lastLength);
                len = Math.min(src.remaining(), len);
                if (len == 0) {
                    break;
                }
                int delta = len - src.remaining();
                readByteCount += len;

                if (delta < 0) {// handling case where dest has no enough room.
                    src.limit(src.limit() + delta);
                }
                dst.put(src);
                if (delta < 0) {
                    src.limit(src.limit() - delta);
                }

            }

            if (readByteCount >= 4 && readByteCount - 4 >= lastLength) {
                readByteCount = 0;
                lastLength = 0;
                return true;
            } else if ( lastLength > maxLength ) {
                throw new RuntimeException("Command exceeding maximum length: " + lastLength);
            }
        }
        return false;
    }

}
