package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

public class AckMessage implements Message {
    private short opCode = 10;
    private short commandOpCode;
    private boolean noOptional = false;
    private boolean followCommand = false;
    private boolean statCommand = false;

    private String usernameForFollow;

    private short age;
    private short numPosts;
    private short numFollowers;
    private short numFollowing;

    public AckMessage(short commandOpCode) {
        this.commandOpCode = commandOpCode;
    }

    public AckMessage(short commandOpCode, String username) {
        this.commandOpCode = commandOpCode;
        followCommand = true;
        usernameForFollow = username;
    }

    public AckMessage(short commandOpCode, short age,
                      short numPosts, short numFollowers, short numFollowing) {
        this.commandOpCode = commandOpCode;
        statCommand = true;
        this.age = age;
        this.numPosts = numPosts;
        this.numFollowers = numFollowers;
        this.numFollowing = numFollowing;
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
