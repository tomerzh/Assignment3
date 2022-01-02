package bgu.spl.net.api.bidi;

public interface Message {
    //gets array of bytes and init fields according to message
    void init();
    short getOpCode();
}
