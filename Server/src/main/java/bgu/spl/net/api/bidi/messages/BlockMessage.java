package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class BlockMessage implements Message {
    private short opCode = 12;
    private LinkedList<Object> arguments;

    public BlockMessage(byte[][] bytes) {
        arguments = new LinkedList<>();
        for (byte[] nextByte: bytes) {
            String str = popString(nextByte);
            arguments.add(str);
        }
    }

    private String popString(byte[] bytes) {
        String result = new String(bytes, StandardCharsets.UTF_8);
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
