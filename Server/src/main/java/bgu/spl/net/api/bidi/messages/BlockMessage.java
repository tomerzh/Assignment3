package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

public class BlockMessage implements Message {
    private short opCode = 12;

    @Override
    public void init() {

    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
