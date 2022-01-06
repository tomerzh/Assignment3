package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class NotificationMessage implements Message {
    private short opCode = 9;
    private byte notificationType;
    private String postingUser;
    private String Content;

    public NotificationMessage(byte notificationType, String postingUser, String content) {
        this.notificationType = notificationType;
        this.postingUser = postingUser;
        this.Content = content;
    }

    @Override
    public void decode(byte[] bytesArr) {
    }

    @Override
    public byte[] encode() {
        byte[] bytesArrOp = new byte[2];
        bytesArrOp[0] = (byte)((opCode >> 8) & 0xFF);
        bytesArrOp[1] = (byte)(opCode & 0xFF);

        byte[] bytesArrType = new byte[notificationType];

        byte[] bytesArrPostingUser = postingUser.getBytes(StandardCharsets.UTF_8);
        int postingUserArrSize = bytesArrPostingUser.length;

        byte[] bytesArrContent = Content.getBytes(StandardCharsets.UTF_8);
        int contentArrSize = bytesArrContent.length;

        byte[] byteArrBackSlash = new byte[2];
        byteArrBackSlash[0] = (byte)(('0' >> 8) & 0xFF);
        byteArrBackSlash[1] = (byte)('0' & 0xFF);
        int backSlashArrSize = byteArrBackSlash.length;

        byte[] bytesArrEnd = ";".getBytes(StandardCharsets.UTF_8);
        int sizeEnd = bytesArrEnd.length;

        ByteBuffer buffer = ByteBuffer.wrap(new byte[2+1+postingUserArrSize+
                                backSlashArrSize+contentArrSize+backSlashArrSize+sizeEnd]);

        buffer.put(bytesArrOp);
        buffer.put(bytesArrType);
        buffer.put(bytesArrPostingUser);
        buffer.put(byteArrBackSlash);
        buffer.put(bytesArrContent);
        buffer.put(byteArrBackSlash);
        buffer.put(bytesArrEnd);

        return buffer.array();
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
