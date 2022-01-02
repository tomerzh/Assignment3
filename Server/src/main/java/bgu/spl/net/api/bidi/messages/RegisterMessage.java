package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;

public class RegisterMessage implements Message {
    private short opCode;
    private String username;
    private String password;
    private String birthday;

    private int startByte;
    private int endByte;

    public RegisterMessage() {

    }

    @Override
    public boolean decode(byte[] bytesArr) {
        byte[] codeInBytes = {bytesArr[0], bytesArr[1]};
        opCode = bytesToShort(codeInBytes);
        startByte = 2;
        endByte = 2;
        int numOfString = 1;

        while (bytesArr[endByte] != ';') {
            if(bytesArr[endByte] == '\0') {
                String str = popString(bytesArr);
                switch (numOfString){
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
                endByte = endByte + 1;
                startByte = endByte;
                numOfString++;
            }
            else {
                endByte++;
            }
        }
        return username != null && password != null && birthday != null;
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
        String result = new String(bytes, startByte, endByte, StandardCharsets.UTF_8);
        return result;
    }

    public short bytesToShort(byte[] byteArr) {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }
}
