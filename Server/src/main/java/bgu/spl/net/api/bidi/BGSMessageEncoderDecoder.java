package bgu.spl.net.api.bidi;

import bgu.spl.net.api.MessageEncoderDecoder;

public class BGSMessageEncoderDecoder<Message> implements MessageEncoderDecoder<Message> {

    @Override
    public Message decodeNextByte(byte nextByte) {
        return null;
    }

    @Override
    public byte[] encode(Message message) {
        return new byte[0];
    }
}
