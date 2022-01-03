package bgu.spl.net.srv;

import sun.misc.Queue;

import java.util.LinkedList;

public class User {
    private boolean loggedIn = false;
    private String username;
    private String password;
    private String birthday;
    private LinkedList<User> following;
    private LinkedList<User> followers;
    private LinkedList<String> posts;
    private LinkedList<String> privateMessages;
    private Queue<String> incomingPosts;

    public User(String username, String password, String birthday){
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        following = new LinkedList<>();
        followers = new LinkedList<>();
        posts = new LinkedList<>();
        privateMessages = new LinkedList<>();
        incomingPosts = new Queue<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getBirthday() {
        return birthday;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void addFollow(User client){
        following.add(client);
    }

    public void addFollower(User client){
        followers.add(client);
    }

    public void newPost(String s){
        posts.add(s);
    }

    public void incomingPost(String s){
        incomingPosts.enqueue(s);
    }

    public void addPM(String s){
        privateMessages.add(s);
    }
}
