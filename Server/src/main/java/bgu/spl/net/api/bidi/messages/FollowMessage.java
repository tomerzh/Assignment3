package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;


public class FollowMessage implements Message {
    private short opCode;
    private byte followOrUnfollow;
    private String username;

    public FollowMessage() {

    }

    @Override
    public boolean decode(byte[] bytesArr) {
        byte[] codeInBytes = {bytesArr[0], bytesArr[1]};
        opCode = bytesToShort(codeInBytes);
        followOrUnfollow = bytesArr[2];
        username = popString(bytesArr);
        return true;
    }

    @Override
    public boolean encode() {
        return false;
    }

    @Override
    public short getOpCode() {
        return opCode;
    }

    private String popString(byte[] bytes) {
        String result = new String(bytes, 3, bytes.length - 1, StandardCharsets.UTF_8);
        return result;
    }

    public short bytesToShort(byte[] byteArr) {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }
}
