package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

public class LoginMessage implements Message {
    @Override
    public void init() {

    }

    @Override
    public short getOpCode() {
        return 0;
    }
}
