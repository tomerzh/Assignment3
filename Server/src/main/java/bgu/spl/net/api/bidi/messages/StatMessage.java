package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

public class StatMessage implements Message {
    private short opCode = 8;

    @Override
    public void init() {

    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
