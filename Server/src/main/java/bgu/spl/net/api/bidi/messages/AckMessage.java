package bgu.spl.net.api.bidi.messages;

import bgu.spl.net.api.bidi.Message;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class AckMessage implements Message {
    private short opCode = 10;
    private short commandOpCode;
    private boolean noOptional = false;
    private boolean followCommand = false;
    private boolean statCommand = false;

    //for follow ack
    private String usernameForFollow;

    //for stat ack
    private short age;
    private short numPosts;
    private short numFollowers;
    private short numFollowing;

    public AckMessage(short commandOpCode) {
        this.commandOpCode = commandOpCode;
    }

    public AckMessage(short commandOpCode, String username) {
        this.commandOpCode = commandOpCode;
        followCommand = true;
        usernameForFollow = username;
    }

    public AckMessage(short commandOpCode, short age,
                      short numPosts, short numFollowers, short numFollowing) {
        this.commandOpCode = commandOpCode;
        statCommand = true;
        this.age = age;
        this.numPosts = numPosts;
        this.numFollowers = numFollowers;
        this.numFollowing = numFollowing;
    }

    @Override
    public boolean decode(byte[] bytesArr) {
        return false;
    }

    @Override
    public byte[] encode() {
        byte[] bytesArrOp = new byte[4];
        bytesArrOp[0] = (byte)((opCode >> 8) & 0xFF);
        bytesArrOp[1] = (byte)(opCode & 0xFF);
        bytesArrOp[2] = (byte)((commandOpCode >> 8) & 0xFF);
        bytesArrOp[3] = (byte)(commandOpCode & 0xFF);

        byte[] bytesArrEnd = ";".getBytes(StandardCharsets.UTF_8);
        int sizeEnd = bytesArrEnd.length;

        ByteBuffer buffer;

        if(followCommand){
            byte[] bytesArrUserName = usernameForFollow.getBytes(StandardCharsets.UTF_8);
            int sizeName = bytesArrUserName.length;
            buffer = ByteBuffer.wrap(new byte[4+sizeName+sizeEnd]);
            buffer.put(bytesArrOp);
            buffer.put(bytesArrUserName);
            buffer.put(bytesArrEnd);
        }

        else if(statCommand){
            byte[] byteArrOptional = new byte[8];
            bytesArrOp[0] = (byte)((age >> 8) & 0xFF);
            bytesArrOp[1] = (byte)(age & 0xFF);
            bytesArrOp[2] = (byte)((numPosts >> 8) & 0xFF);
            bytesArrOp[3] = (byte)(numPosts & 0xFF);
            bytesArrOp[4] = (byte)((numFollowers >> 8) & 0xFF);
            bytesArrOp[5] = (byte)(numFollowers & 0xFF);
            bytesArrOp[6] = (byte)((numFollowing >> 8) & 0xFF);
            bytesArrOp[7] = (byte)(numFollowing & 0xFF);

            int sizeOptional = byteArrOptional.length;
            buffer = ByteBuffer.wrap(new byte[4+sizeOptional+sizeEnd]);
            buffer.put(bytesArrOp);
            buffer.put(byteArrOptional);
            buffer.put(bytesArrEnd);
        }

        else{
            buffer = ByteBuffer.wrap(new byte[4+sizeEnd]);
            buffer.put(bytesArrOp);
            buffer.put(bytesArrEnd);
        }
        return buffer.array();
    }

    @Override
    public short getOpCode() {
        return opCode;
    }
}
