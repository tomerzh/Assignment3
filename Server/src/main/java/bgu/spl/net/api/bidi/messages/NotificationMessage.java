package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class NotificationMessage implements Message {
    private short opCode = 9;
    private LinkedList<Object> arguments;

    public NotificationMessage(byte[][] bytes) {
        arguments = new LinkedList<>();
        byte nextByte = bytes[0][0];
        arguments.add(nextByte);
        String strWithByte = popStringWithByte(bytes[0]);
        arguments.add(strWithByte);
        String str = popString(bytes[1]);
        arguments.add(str);
    }

    private String popString(byte[] bytes) {
        String result = new String(bytes, StandardCharsets.UTF_8);
        return result;
    }

    private String popStringWithByte(byte[] bytes) {
        String result = new String(bytes, 1, bytes.length, StandardCharsets.UTF_8);
        return result;
    }

    @Override
    public boolean decode(byte[] bytesArr) {
        return false;
    }

    @Override
    public boolean encode() {
        return false;
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
