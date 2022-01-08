package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ErrorMessage implements Message {
    private short opCode = 11;
    private short commandOpCode;

    public ErrorMessage(short commandOpCode) {
        this.commandOpCode = commandOpCode;
    }

    @Override
    public void decode(byte[] bytesArr) {
    }

    @Override
    public byte[] encode() {
        byte[] bytesArr0 = new byte[4];
        bytesArr0[0] = (byte)((opCode >> 8) & 0xFF);
        bytesArr0[1] = (byte)(opCode & 0xFF);
        bytesArr0[2] = (byte)((commandOpCode >> 8) & 0xFF);
        bytesArr0[3] = (byte)(commandOpCode & 0xFF);

        byte[] bytesArr1 = ";".getBytes(StandardCharsets.UTF_8);
        int size = bytesArr1.length;

        ByteBuffer buffer = ByteBuffer.wrap(new byte[4+size]);
        buffer.put(bytesArr0);
        buffer.put(bytesArr1);

        return buffer.array();
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
    
}
