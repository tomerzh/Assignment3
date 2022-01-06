package bgu.spl.net.api.bidi;

public interface Message {
    void decode(byte[] bytesArr);

    byte[] encode();

    short getOpCode();
}
