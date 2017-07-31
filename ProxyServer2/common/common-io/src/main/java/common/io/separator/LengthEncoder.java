package common.io.separator;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class LengthEncoder implements Encoder {

    private int maxLength=16384;

    public LengthEncoder(int maxLength) {
        this.maxLength = maxLength;
    }

    public List<ByteBuffer> encode(List<ByteBuffer> src) {
        int totalLength=0;
        for ( ByteBuffer buffer : src ) {
            totalLength += buffer.remaining();
        }
        if (totalLength > maxLength) {
            throw new RuntimeException("Command exceeding maximum length: " + totalLength);
        }
        // put length size
        ByteBuffer sizeBuf = ByteBuffer.allocate(4).putInt(0, totalLength);
        ArrayList<ByteBuffer> result = new ArrayList<>(src.size()+1);
        result.add(sizeBuf);
        result.addAll(src);
        return result;
    }

}
