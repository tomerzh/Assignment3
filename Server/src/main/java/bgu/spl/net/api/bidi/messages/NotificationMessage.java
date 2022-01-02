package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

public class NotificationMessage implements Message {
    private short opCode = 9;

    @Override
    public void init() {

    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
