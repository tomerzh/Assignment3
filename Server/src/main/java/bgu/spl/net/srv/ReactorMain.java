package bgu.spl.net.srv;

import bgu.spl.net.api.bidi.BGSBidiMessagingProtocol;
import bgu.spl.net.api.bidi.BGSMessageEncoderDecoder;
import bgu.spl.net.api.bidi.Message;

public class ReactorMain {
    public static void main(String[] args) {
        int port = 7777;
        int numOfThreads = 3;
        if (args.length == 2) {
            port = Integer.parseInt(args[0]);
            numOfThreads = Integer.parseInt(args[1]);
        }

        Reactor<Message> server= new Reactor<>(numOfThreads, port,
                BGSBidiMessagingProtocol::new, //protocol factory
                BGSMessageEncoderDecoder::new //message encoder decoder factory
        );
        server.serve();
    }

}
