package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class FollowMessage implements Message {
    private short opCode = 4;
    private LinkedList<Object> arguments;

    public FollowMessage(byte[][] bytes) {
        arguments = new LinkedList<>();
        byte nextByte = bytes[0][0];
        arguments.add(nextByte);
        String str = popString(bytes[0]);
        arguments.add(str);
    }

    private String popString(byte[] bytes) {
        String result = new String(bytes, 1, bytes.length, StandardCharsets.UTF_8);
        return result;
    }

    @Override
    public LinkedList<Object> init() {
        return arguments;
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
