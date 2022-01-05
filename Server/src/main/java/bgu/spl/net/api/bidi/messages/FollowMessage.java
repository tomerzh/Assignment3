package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class FollowMessage implements Message {
    private short opCode;
    private boolean followOrUnfollow = false;
    private String username;

    private int len;

    public FollowMessage() {

    }

    @Override
    public boolean decode(byte[] bytesArr) {
        byte[] codeInBytes = {bytesArr[0], bytesArr[1]};
        opCode = bytesToShort(codeInBytes);
        if(bytesArr[2] == 0){
            followOrUnfollow = true;
        }
        byte[] bytesUsername = Arrays.copyOfRange(bytesArr, 3, bytesArr.length);
        len = 0;
        while (bytesUsername[len] != '\0') {
            len++;
        }
        username = popString(bytesUsername);
        return true;
    }

    @Override
    public byte[] encode() {
        return null;
    }

    @Override
    public short getOpCode() {
        return opCode;
    }

    public boolean isFollowOrUnfollow() {
        return followOrUnfollow;
    }

    public String getUsername() {
        return username;
    }

    private String popString(byte[] bytes) {
        String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
        return result;
    }

    public short bytesToShort(byte[] byteArr) {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }
}
