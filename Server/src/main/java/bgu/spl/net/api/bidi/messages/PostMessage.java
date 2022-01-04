package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class PostMessage implements Message {
    private short opCode;
    private String content;
    private LinkedList<String> usersToSend;

    private int endByte;

    public PostMessage() {
        usersToSend = new LinkedList<String>();
    }

    @Override
    public boolean decode(byte[] bytesArr) {
        byte[] codeInBytes = {bytesArr[0], bytesArr[1]};
        opCode = bytesToShort(codeInBytes);

        endByte = 2;
        while (bytesArr[endByte] != ';') {
            if (bytesArr[endByte] == '\0') {
                content = popString(bytesArr);
            }
            endByte++;
        }
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
        String result = new String(bytes, 2, (endByte-1), StandardCharsets.UTF_8);
        return result;
    }

    public short bytesToShort(byte[] byteArr) {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }

    public LinkedList<String> getUsersToSend(){
        return usersToSend;
    }

    public String getContent(){
        return content;
    }
}
