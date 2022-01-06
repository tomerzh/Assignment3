package bgu.spl.net.srv;

import bgu.spl.net.api.bidi.BGSBidiMessagingProtocol;
import bgu.spl.net.api.bidi.BGSMessageEncoderDecoder;
import bgu.spl.net.api.bidi.Message;

public class TPCMain {

    public static void main(String[] args) {
        int port = 7777;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        System.out.println(port);
        TPCServer<Message> server= new TPCServer<Message>(port,
                BGSBidiMessagingProtocol::new, //protocol factory
                BGSMessageEncoderDecoder::new //message encoder decoder factory
        );
        server.serve();
    }
}
