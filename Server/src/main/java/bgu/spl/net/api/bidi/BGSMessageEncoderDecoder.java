package bgu.spl.net.api.bidi;

import bgu.spl.net.api.MessageEncoderDecoder;

public class BGSMessageEncoderDecoder<T> implements MessageEncoderDecoder {

    @Override
    public Object decodeNextByte(byte nextByte) {
        return null;
    }

    @Override
    public byte[] encode(Object message) {
        return new byte[0];
    }
}
