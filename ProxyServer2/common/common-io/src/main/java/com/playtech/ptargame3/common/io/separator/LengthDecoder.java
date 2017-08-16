package com.playtech.ptargame3.common.io.separator;

import com.playtech.ptargame3.common.util.HexUtil;

import java.nio.ByteBuffer;
import java.util.logging.Logger;

public final class LengthDecoder implements Decoder {

    private int maxLength=16384;

    private ByteBuffer lengthBytes = ByteBuffer.allocate(4);
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
                byte in = src.get();
                lengthBytes.put(in);
                readByteCount++;
                if (readByteCount == 4) {
                    lengthBytes.order(src.order()).flip();
                    lastLength = lengthBytes.getInt();
                }
            } else {
                // copy
                int len = Math.min(dst.remaining(), remaining());
                len = Math.min(src.remaining(), len);
                if (len == 0 && lastLength != 0) {
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

            if (readByteCount >= 4 && remaining() <= 0) {
                readByteCount = 0;
                lastLength = 0;
                lengthBytes.clear();
                return true;
            } else if ( lastLength > maxLength ) {
                throw new RuntimeException("Command exceeding maximum length: " + lastLength);
            }
        }
        return false;
    }

    private int remaining(){
        return lastLength - (readByteCount - 4);
    }

}
