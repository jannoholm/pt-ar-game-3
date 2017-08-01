package com.playtech.ptargame3.common.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class StringUtil {

    public static boolean isNull(String s) {
        return s == null || s.length() == 0;
    }

    public static String readUTF8String(ByteBuffer buffer) {
        int beginning = buffer.position();
        while (true) {
            byte b = buffer.get();
            if (b == getTerminatorByte()) {
                int end = buffer.position();
                buffer.position(beginning);
                byte[] tmp = new byte[end-beginning-1];
                buffer.get(tmp); buffer.get();
                return new String(tmp, StandardCharsets.UTF_8);
            } else if ( (b & 0b10000000) == 0 ) {
                // one byte char
            } else if ( (b & 0b11100000) > 0 ) {
                // 2 byte char
                buffer.get();
            } else if ( (b & 0b11110000) > 0 ) {
                // 3 byte char
                buffer.get();
                buffer.get();
            } else if ( (b & 0b11111000) > 0 ) {
                // 4 byte char
                buffer.get();
                buffer.get();
                buffer.get();
            }
        }
    }

    public static void writeUTF8String(String str, ByteBuffer buffer) {
        if (str == null) str = "";
        buffer.put(str.getBytes(StandardCharsets.UTF_8));
        buffer.put((byte) 0); // terminator
    }

    public static byte[] getUTF8StringBytes(String str) {
        if (str == null) str = "";
        return str.getBytes(StandardCharsets.UTF_8);
    }

    public static byte getTerminatorByte() {
        return 0;
    }

    /*
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        writeUTF8String("Hello world!", buffer);
        writeUTF8String("öäüõÖÄÜÕ", buffer);
        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        System.out.println(HexUtil.toHex(bytes));
        buffer.flip();
        System.out.println(buffer.remaining());
        String s1 = readUTF8String(buffer);
        String s2 = readUTF8String(buffer);
        System.out.println(s1.length() + "::" + s1);
        System.out.println(s2.length() + "::" + s2);
    }
    */

}
