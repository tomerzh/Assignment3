package bgu.spl.net.srv;

import java.util.LinkedList;

public class Client {
    private boolean loggedIn = false;
    private LinkedList<Client> following;
    private LinkedList<Client> followers;
    private LinkedList<String> posts;
    private LinkedList<String> privateMessages;
    private LinkedList<String> incomingPosts;

    //private ConnectionHandler<String> connectionHandler;

    public Client(ConnectionHandler<String> connectionHandler){
        //this.connectionHandler = connectionHandler;
        following = new LinkedList<>();
        followers = new LinkedList<>();
        posts = new LinkedList<>();
        privateMessages = new LinkedList<>();
        incomingPosts = new LinkedList<>();
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void addFollow(Client client){
        following.add(client);
    }

    public void addFollower(Client client){
        followers.add(client);
    }

    public void newPost(String s){
        posts.add(s);
    }

    public void incomingPost(String s){
        incomingPosts.add(s);
    }

    public void addPM(String s){
        privateMessages.add(s);
    }
}
