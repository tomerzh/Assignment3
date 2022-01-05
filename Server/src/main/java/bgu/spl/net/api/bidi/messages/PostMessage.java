package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

public class PostMessage implements Message {
    private short opCode;
    private String content;
    private LinkedList<String> usersToSend;

    private int len;

    public PostMessage() {
        usersToSend = new LinkedList<String>();
    }

    @Override
    public boolean decode(byte[] bytesArr) {
        byte[] codeInBytes = {bytesArr[0], bytesArr[1]};
        opCode = bytesToShort(codeInBytes);
        byte[] bytesContent = Arrays.copyOfRange(bytesArr, 2, bytesArr.length);
        len = 0;
        while (bytesContent[len] != '\0') {
            len++;
        }
        content = popString(bytesContent);
        findTaggedUsers();
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

    private void findTaggedUsers() {
        String[] split = content.split(" ");
        for (String word : split) {
            if (word.charAt(0) == '@') {
                String username = word.substring(1);
                usersToSend.add(username);
            }
        }
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

    public LinkedList<String> getUsersToSend(){
        return usersToSend;
    }

    public String getContent(){
        return content;
    }
}
