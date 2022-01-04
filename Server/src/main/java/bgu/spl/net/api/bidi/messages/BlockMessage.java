package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.charset.StandardCharsets;

public class BlockMessage implements Message {
    private short opCode;
    private String username;
    private int endByte;

    public BlockMessage() {

    }

    @Override
    public boolean decode(byte[] bytesArr) {
        byte[] codeInBytes = {bytesArr[0], bytesArr[1]};
        opCode = bytesToShort(codeInBytes);

        endByte = 2;
        while (bytesArr[endByte] != ';') {
            if (bytesArr[endByte] == '\0') {
                username = popString(bytesArr);
            }
            endByte++;
        }
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

    private String popString(byte[] bytes) {
        String result = new String(bytes, 2, endByte, StandardCharsets.UTF_8);
        return result;
    }

    public short bytesToShort(byte[] byteArr) {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }
}
