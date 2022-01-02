package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

public class LogoutMessage implements Message {
    private short opCode = 3;

    public LogoutMessage(byte[] bytes) {

    }

    @Override
    public void init() {

    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
