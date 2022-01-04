package bgu.spl.net.api.bidi;

public interface Message {
    boolean decode(byte[] bytesArr);

    byte[] encode();

    short getOpCode();
}
