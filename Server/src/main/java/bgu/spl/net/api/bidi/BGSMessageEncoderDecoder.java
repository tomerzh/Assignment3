package bgu.spl.net.api.bidi;

import bgu.spl.net.api.MessageEncoderDecoder;

public class BGSMessageEncoderDecoder<T> implements MessageEncoderDecoder<T> {

    @Override
    public T decodeNextByte(byte nextByte) {
        return null;
    }

    @Override
    public byte[] encode(T message) {
        return new byte[0];
    }
}
