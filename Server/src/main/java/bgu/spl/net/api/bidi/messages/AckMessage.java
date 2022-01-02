package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

public class AckMessage implements Message {
    private short opCode = 10;
    @Override
    public void init() {

    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
