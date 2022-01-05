package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

public class StatMessage implements Message {
    private short opCode;
    private String listUsers;
    private LinkedList<String> listOfUsernames;

    private int len;

    public StatMessage() {

    }

    @Override
    public void decode(byte[] bytesArr) {
        listOfUsernames = new LinkedList<>();
        byte[] codeInBytes = {bytesArr[0], bytesArr[1]};
        opCode = bytesToShort(codeInBytes);

        byte[] bytesList = Arrays.copyOfRange(bytesArr, 2, bytesArr.length);
        len = 0;
        while (bytesList[len] != '\0') {
            len++;
        }
        listUsers = popString(bytesList);
        findUsers();
    }

    @Override
    public byte[] encode() {
        return null;
    }

    @Override
    public short getOpCode() {
        return opCode;
    }

    public LinkedList<String> getListOfUsernames() {
        return listOfUsernames;
    }

    private void findUsers() {
        String[] split = listUsers.split("\\|");
        listOfUsernames.addAll(Arrays.asList(split));
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
