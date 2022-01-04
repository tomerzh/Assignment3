package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;

public class RegisterMessage implements Message {
    private short opCode;
    private String username;
    private String password;
    private String birthday;

    private int startByte;
    private int len;

    public RegisterMessage() {

    }

    @Override
    public boolean decode(byte[] bytesArr) {
        byte[] codeInBytes = {bytesArr[0], bytesArr[1]};
        opCode = bytesToShort(codeInBytes);
        startByte = 2;
        len = 0;
        int numOfArgument = 1;

        while (bytesArr[startByte+len] != ';') {
            if(bytesArr[startByte+len] == '\0') {
                String str = popString(bytesArr);
                switch (numOfArgument){
                    case 1:
                        username = str;
                        break;
                    case 2:
                        password = str;
                        break;
                    case 3:
                        birthday = str;
                        break;
                }
                len = len + 1;
                startByte = len;
                numOfArgument++;
            }
            else {
                len++;
            }
        }
        return username != null && password != null && birthday != null;
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

    public String getPassword() {
        return password;
    }

    public String getBirthday() {
        return birthday;
    }

    private String popString(byte[] bytes) {
        String result = new String(bytes, startByte, len, StandardCharsets.UTF_8);
        len = 0;
        return result;
    }

    public short bytesToShort(byte[] byteArr) {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }
}
