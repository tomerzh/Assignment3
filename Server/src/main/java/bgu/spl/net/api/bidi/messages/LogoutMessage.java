package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.util.LinkedList;

public class LogoutMessage implements Message {
    private short opCode = 3;

    public LogoutMessage(byte[][] bytes) {

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
