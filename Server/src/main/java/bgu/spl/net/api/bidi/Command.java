package bgu.spl.net.api.bidi;

public interface Command {

    boolean process(Message message);
}
