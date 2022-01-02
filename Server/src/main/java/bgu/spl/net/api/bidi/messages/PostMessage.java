package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

public class PostMessage implements Message {
    private short opCode = 5;

    @Override
    public void init() {

    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
