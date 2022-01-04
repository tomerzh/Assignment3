package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

public class ErrorMessage implements Message {
    private short opCode = 11;
    private short commandOpCode;

    public ErrorMessage(short commandOpCode) {
        this.commandOpCode = commandOpCode;
    }

    @Override
    public boolean decode(byte[] bytesArr) {
        return false;
    }

    @Override
    public byte[] encode() {
        return null;
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
