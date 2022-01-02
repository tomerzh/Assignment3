package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class LoginMessage implements Message {
    private short opCode = 2;
    private LinkedList<Object> arguments;

    public LoginMessage(byte[][] bytes) {
        arguments = new LinkedList<>();
        for(int i = 0; i < bytes.length; i++){
            if(i == bytes.length - 1){
                arguments.add(bytes[i]);
            }
            else {
                String str = popString(bytes[i]);
                arguments.add(str);
            }
        }
    }

    private String popString(byte[] bytes) {
        String result = new String(bytes, StandardCharsets.UTF_8);
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
