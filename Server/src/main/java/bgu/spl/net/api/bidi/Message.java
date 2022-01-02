package bgu.spl.net.api.bidi;

import java.util.LinkedList;

public interface Message {
    //gets array of bytes and init fields according to message
    LinkedList<Object> init();
    short getOpCode();
}
