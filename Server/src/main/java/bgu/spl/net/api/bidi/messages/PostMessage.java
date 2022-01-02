package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class PostMessage implements Message {
    private short opCode = 5;
    private LinkedList<Object> arguments;

    public PostMessage(byte[][] bytes) {
        arguments = new LinkedList<>();
        String str = popString(bytes[0]);
        arguments.add(str);
    }

    @Override
    public boolean decode(byte[] bytesArr) {
        return false;
    }

    @Override
    public boolean encode() {
        return false;
    }

    private String popString(byte[] bytes) {
        String result = new String(bytes, StandardCharsets.UTF_8);
        return result;
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
