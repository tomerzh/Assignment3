package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PmMessage implements Message {
    private short opCode;
    private String username;
    private String content;

    private int len;

    public PmMessage() {

    }

    @Override
    public boolean decode(byte[] bytesArr) {
        byte[] codeInBytes = {bytesArr[0], bytesArr[1]};
        opCode = bytesToShort(codeInBytes);

        byte[] bytesUsername = Arrays.copyOfRange(bytesArr, 2, bytesArr.length);
        len = 0;
        while (bytesUsername[len] != '\0') {
            len++;
        }
        username = popString(bytesUsername);
        byte[] bytesContent = Arrays.copyOfRange(bytesUsername, len+1, bytesUsername.length);
        len = 0;
        while (bytesContent[len] != '\0') {
            len++;
        }
        content = popString(bytesContent);
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

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
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
