package bgu.spl.net.api.bidi;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.bidi.commands.*;
import bgu.spl.net.api.bidi.messages.RegisterMessage;

import java.util.HashMap;

public class BGSMessageEncoderDecoder<T extends Message> implements MessageEncoderDecoder<T> {
    @Override
    public T decodeNextByte(byte nextByte) throws InstantiationException, IllegalAccessException {
//        Class messageClass = opCodeToMessage.get(i);
//        Message message = null;
//        if(messageClass != null){
//            message = (Message) messageClass.newInstance();
//            //message.decode - boolean
//        }
//        else {
//            //message error
//        }
//        return (T) message;
        return null;
    }

    @Override
    public byte[] encode(T message) {
        return new byte[0];
    }
}
