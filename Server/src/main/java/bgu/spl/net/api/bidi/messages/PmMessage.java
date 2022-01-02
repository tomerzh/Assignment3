package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

public class PmMessage implements Message {
    private short opCode = 6;

    @Override
    public void init() {

    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
