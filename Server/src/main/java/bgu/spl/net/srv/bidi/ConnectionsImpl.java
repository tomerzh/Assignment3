package bgu.spl.net.srv.bidi;

import bgu.spl.net.api.bidi.Connections;

public class ConnectionsImpl<T> implements Connections<T> {


    public ConnectionsImpl() {

    }

    @Override
    public boolean send(int connectionId, T msg) {
        return false;
    }

    @Override
    public void broadcast(T msg) {

    }

    @Override
    public void disconnect(int connectionId) {

    }
}
