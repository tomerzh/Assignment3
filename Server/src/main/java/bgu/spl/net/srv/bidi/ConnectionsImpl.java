package bgu.spl.net.srv.bidi;

import bgu.spl.net.api.bidi.Connections;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectionsImpl<T> implements Connections<T> {
    private ConcurrentHashMap<Integer, ConnectionHandler<T>> connIdToHandler;
    private ConcurrentHashMap<String, Integer> usernameToId;
    private Integer nextAvailableId;

    public ConnectionsImpl() {
        connIdToHandler = new ConcurrentHashMap<>();
        usernameToId = new ConcurrentHashMap<>();
        nextAvailableId = 0;
    }

    public Integer addNewClient(ConnectionHandler<T> connectionHandler){
        connIdToHandler.put(nextAvailableId, connectionHandler);
        Integer connId = nextAvailableId;
        nextAvailableId++;
        return connId;
    }

    public void removeClient(int connId){
        connIdToHandler.remove(connId);
    }

    public void addNewUser(String username, int connId){
        usernameToId.put(username, connId);
    }

    public void removeUser(String username){
        usernameToId.remove(username);
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
