package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

public class FollowMessage implements Message {
    private short opCode = 4;

    @Override
    public void init() {

    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
