package bgu.spl.net.api.bidi;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.bidi.messages.*;
import java.util.Arrays;

public class BGSMessageEncoderDecoder<T extends Message> implements MessageEncoderDecoder<T> {
    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;

    @Override
    public T decodeNextByte(byte nextByte) {
        if (nextByte == ';') {
            short optCode = bytesToShort(new byte[]{bytes[0], bytes[1]});
            Message message;
            switch (optCode) {
                case 1:
                    message = new RegisterMessage();
                    message.decode(bytes);
                    break;
                case 2:
                    message = new LoginMessage();
                    message.decode(bytes);
                    break;
                case 3:
                    message = new LogoutMessage();
                    message.decode(bytes);
                    break;
                case 4:
                    message = new FollowMessage();
                    message.decode(bytes);
                    break;
                case 5:
                    message = new PostMessage();
                    message.decode(bytes);
                    break;
                case 6:
                    message = new PmMessage();
                    message.decode(bytes);
                    break;
                case 7:
                    message = new LogstatMessage();
                    message.decode(bytes);
                    break;
                case 8:
                    message = new StatMessage();
                    message.decode(bytes);
                    break;
                case 12:
                    message = new BlockMessage();
                    message.decode(bytes);
                    break;
                default:
                    message = new ErrorMessage(optCode);
            }
            len = 0;
            return (T) message;
        }

        pushByte(nextByte);
        return null;
    }

    @Override
    public byte[] encode(T message) {
        return message.encode();
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }
        bytes[len++] = nextByte;
    }

    public short bytesToShort(byte[] byteArr) {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }
}
