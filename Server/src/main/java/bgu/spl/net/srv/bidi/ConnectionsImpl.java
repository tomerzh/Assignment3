package bgu.spl.net.srv.bidi;

import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.Client;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectionsImpl<T> implements Connections<T> {
    private ConcurrentHashMap<Integer, Client> idToClient;

    public ConnectionsImpl() {
        idToClient = new ConcurrentHashMap<>();
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
