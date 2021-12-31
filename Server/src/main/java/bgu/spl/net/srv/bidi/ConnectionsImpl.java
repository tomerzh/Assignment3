package bgu.spl.net.srv.bidi;

import bgu.spl.net.api.bidi.Connections;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectionsImpl<T> implements Connections<T> {
    private ConcurrentHashMap<Integer, ConnectionHandler<T>> connIdToHandler;
    private ConcurrentHashMap<String, Integer> usernameToId;

    public ConnectionsImpl() {
        connIdToHandler = new ConcurrentHashMap<>();
        usernameToId = new ConcurrentHashMap<>();
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
