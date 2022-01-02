package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

public class RegisterMessage implements Message {
    private short opCode = 1;
    private LinkedList<String> arguments;

    public RegisterMessage(byte[][] bytes) {
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
    public void init() {

    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
