package bgu.spl.net.api.bidi;

public class BGSBidiMessagingProtocol<T> implements BidiMessagingProtocol<T>{

    private boolean shouldTerminate = false;
    private int connectionId = -1;
    private Connections connections = null;


    public BGSBidiMessagingProtocol(){
        //to do?
    }

    @Override
    public void start(int connectionId, Connections<T> connections) {
        this.connectionId = connectionId;
        this.connections = connections;
    }

    @Override
    public void process(T message) {


    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
