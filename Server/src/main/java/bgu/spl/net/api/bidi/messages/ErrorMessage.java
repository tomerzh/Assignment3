package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

public class ErrorMessage implements Message {
    private short opCode;

    public ErrorMessage() {

    }

    @Override
    public boolean decode(byte[] bytesArr) {
        return false;
    }

    @Override
    public boolean encode() {
        return false;
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
