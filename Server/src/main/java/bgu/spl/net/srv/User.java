package bgu.spl.net.srv;

import bgu.spl.net.api.bidi.messages.NotificationMessage;
import bgu.spl.net.api.bidi.messages.PmMessage;
import sun.misc.Queue;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class User {
    private boolean loggedIn = false;
    private String username;
    private String password;
    private String birthday;
    private LinkedBlockingQueue<User> following;
    private LinkedBlockingQueue<User> followers;
    private LinkedBlockingQueue<String> posts;
    private LinkedBlockingQueue<PmMessage> privateMessages;
    private LinkedBlockingQueue<NotificationMessage> incomingPosts;
    private LinkedBlockingQueue<NotificationMessage> incomingPMs;
    private LinkedBlockingQueue<User> blocked;

    public User(String username, String password, String birthday){
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        following = new LinkedBlockingQueue<>();
        followers = new LinkedBlockingQueue<>();
        posts = new LinkedBlockingQueue<>();
        privateMessages = new LinkedBlockingQueue<>();
        incomingPosts = new LinkedBlockingQueue<>();
        incomingPMs = new LinkedBlockingQueue<>();
        blocked = new LinkedBlockingQueue<>();
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

    public LinkedBlockingQueue<User> getFollowing() {
        return following;
    }

    public LinkedBlockingQueue<User> getFollowers() {
        return followers;
    }

    public LinkedBlockingQueue<String> getPosts() {
        return posts;
    }

    public LinkedBlockingQueue<PmMessage> getPrivateMessages() {
        return privateMessages;
    }

    public LinkedBlockingQueue<NotificationMessage> getIncomingPosts() {
        return incomingPosts;
    }

    public LinkedBlockingQueue<NotificationMessage> getIncomingPMs() {
        return incomingPMs;
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

    public boolean removeFollow(User client){
        return following.remove(client);
    }

    public void addFollower(User client){
        followers.add(client);
    }

    public boolean removeFollower(User client){
        return followers.remove(client);
    }

    public void newPost(String s){
        posts.add(s);
    }

    public void addIncomingPost(NotificationMessage post){
        incomingPosts.add(post);
    }

    public void addPM(PmMessage pmMessage){
        privateMessages.add(pmMessage);
    }

    public void addIncomingPM(NotificationMessage pmMessage){
        incomingPMs.add(pmMessage);
    }

    public void addBlock(User user){
        blocked.add(user);
    }

    public boolean isBlocked(User user){
        if(blocked.isEmpty()){
            return false;
        }

        else{
            return blocked.contains(user);
        }
    }

    public boolean isBlocked(String user){
        if(blocked.isEmpty()){
        }

        else{
            for(User blockedUser : blocked){
                if(blockedUser.getUsername() == user){
                    return true;
                }
            }
        }
        return false;
    }


}
