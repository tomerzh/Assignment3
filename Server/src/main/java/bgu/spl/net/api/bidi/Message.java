package bgu.spl.net.api.bidi;

public interface Message {
    boolean decode(byte[] bytesArr);

    boolean encode();

    short getOpCode();
}
