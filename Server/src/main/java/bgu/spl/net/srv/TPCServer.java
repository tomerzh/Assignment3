package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;

import java.util.function.Supplier;

public class TPCServer<T> extends BaseServer<T>{

    public TPCServer(int port, Supplier<BidiMessagingProtocol<T>> protocolFactory,
                     Supplier<MessageEncoderDecoder<T>> encdecFactory) {
        super(port, protocolFactory, encdecFactory);
    }

    @Override
    protected void execute(BlockingConnectionHandler<T> handler) {
        Thread clientThread = new Thread(handler);
        clientThread.start();

    }
}
