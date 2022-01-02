package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.util.LinkedList;

public class ErrorMessage implements Message {
    private short opCode = 11;

    public ErrorMessage(byte[][] bytes) {
    }

    @Override
    public LinkedList<Object> init() {
        return null;
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
