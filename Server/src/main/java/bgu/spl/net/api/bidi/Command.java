package bgu.spl.net.api.bidi;

public interface Command {

    void process(Message message, int connId, Connections connections);
}
