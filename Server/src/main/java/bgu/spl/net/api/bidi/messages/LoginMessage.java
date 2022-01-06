package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class LoginMessage implements Message {
    private short opCode;
    private String username;
    private String password;
    private byte captcha;

    private int len;

    public LoginMessage() {

    }

    @Override
    public void decode(byte[] bytesArr) {
        byte[] codeInBytes = {bytesArr[0], bytesArr[1]};
        opCode = bytesToShort(codeInBytes);

        byte[] bytesUsername = Arrays.copyOfRange(bytesArr, 2, bytesArr.length);
        len = 0;
        while (bytesUsername[len] != '\0') {
            len++;
        }
        username = popString(bytesUsername);
        byte[] bytesPassword = Arrays.copyOfRange(bytesUsername, len+1, bytesUsername.length);
        len = 0;
        while (bytesPassword[len] != '\0') {
            len++;
        }
        password = popString(bytesPassword);
        captcha = bytesPassword[len+1];
    }

    @Override
    public byte[] encode() {
        return null;
    }

    @Override
    public short getOpCode() {
        return opCode;
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

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public Byte getCaptcha(){
        return captcha;
    }
}
