package com.playtech.ptargame3.common.io.separator;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public final class LengthEncoder implements Encoder {

    private final int maxLength;

    public LengthEncoder(int maxLength) {
        this.maxLength = maxLength;
    }

    public List<ByteBuffer> encode(List<ByteBuffer> src) {
        ByteOrder order = ByteOrder.BIG_ENDIAN; // never stays. taken from src buffer or nothing is done.
        int totalLength=0;
        for ( ByteBuffer buffer : src ) {
            totalLength += buffer.remaining();
            order = buffer.order();
        }
        if (totalLength > maxLength) {
            throw new RuntimeException("Command exceeding maximum length: " + totalLength);
        }
        if (totalLength == 0) {
            return src;
        } else {
            // put length size
            ArrayList<ByteBuffer> result = new ArrayList<>(src.size() + 1);
            result.add((ByteBuffer)ByteBuffer.allocate(4).order(order).putInt(totalLength).flip());
            result.addAll(src);
            return result;
        }
    }

}
