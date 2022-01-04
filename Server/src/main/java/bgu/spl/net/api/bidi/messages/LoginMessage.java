package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;

public class LoginMessage implements Message {
    private short opCode;
    private String username;
    private String password;
    private byte captcha;

    private int startByte;
    private int endByte;

    public LoginMessage() {

    }

    @Override
    public boolean decode(byte[] bytesArr) {
        byte[] codeInBytes = {bytesArr[0], bytesArr[1]};
        opCode = bytesToShort(codeInBytes);

        startByte = 2;
        endByte = 2;
        int numOfArgument = 1;

        while (bytesArr[endByte] != ';') {
            if(bytesArr[endByte] == '\0') {
                String str = popString(bytesArr);
                switch (numOfArgument){
                    case 1:
                        username = str;
                        break;
                    case 2:
                        password = str;
                        break;
                }
                endByte = endByte + 1;
                startByte = endByte;
                numOfArgument++;
            }
            else {
                endByte++;
            }
        }
        captcha = bytesArr[startByte]; //the last byte before the ';' char

        return username != null && password != null;
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
        String result = new String(bytes, startByte, (endByte-1), StandardCharsets.UTF_8);
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
