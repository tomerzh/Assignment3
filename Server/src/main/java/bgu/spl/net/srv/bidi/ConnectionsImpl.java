package bgu.spl.net.srv.bidi;

import bgu.spl.net.api.bidi.BGSBidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectionsImpl<T> implements Connections<T> {
    private ConcurrentHashMap<Integer, ConnectionHandler<T>> connIdToHandler;
    private ConcurrentHashMap<String, Integer> usernameToId;
    private ConcurrentHashMap<Integer, String> idToUsername;
    private Integer nextAvailableId;

    public ConnectionsImpl() {
        connIdToHandler = new ConcurrentHashMap<>();
        usernameToId = new ConcurrentHashMap<>();
        idToUsername = new ConcurrentHashMap<>();
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
        System.out.println("Connections, client removed");
    }

    public void addNewUser(String username, int connId){
        usernameToId.put(username, connId);
        idToUsername.put(connId, username);
    }

    public void removeUser(String username){
        int id = usernameToId.get(username);
        usernameToId.remove(username);
        idToUsername.remove(id);
        System.out.println("Connections, user removed");
    }

    public String getUsername(int id) {
        if (!idToUsername.containsKey(id)) {
            return null;
        }
        return idToUsername.get(id);
    }

    public ConnectionHandler getConnectionHandler(int id){
        return connIdToHandler.get(id);
    }


    public int getId(String userName){
        if(!usernameToId.containsKey(userName)){
            return -1;
        }
        return usernameToId.get(userName);
    }

    @Override
    public boolean send(int connectionId, T msg) {
        if(!connIdToHandler.containsKey(connectionId)){
            return false;
        }
        connIdToHandler.get(connectionId).send(msg);
        return true;
    }

    @Override
    public void broadcast(T msg) {

    }

    @Override
    public void disconnect(int connectionId) {
        connIdToHandler.get(connectionId).disconnect();
    }
}
