package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

public class ErrorMessage implements Message {
    private short opCode = 11;

    @Override
    public void init() {

    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
