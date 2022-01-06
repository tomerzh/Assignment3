package bgu.spl.net.srv;

import bgu.spl.net.api.bidi.messages.NotificationMessage;
import bgu.spl.net.api.bidi.messages.PmMessage;
import java.util.concurrent.ConcurrentLinkedQueue;

public class User {
    private boolean loggedIn = false;
    private String username;
    private String password;
    private String birthday;
    private ConcurrentLinkedQueue<User> following;
    private ConcurrentLinkedQueue<User> followers;
    private ConcurrentLinkedQueue<String> posts;
    private ConcurrentLinkedQueue<PmMessage> privateMessages;
    private ConcurrentLinkedQueue<NotificationMessage> incomingPosts;
    private ConcurrentLinkedQueue<NotificationMessage> incomingPMs;
    private ConcurrentLinkedQueue<User> blocked;

    public User(String username, String password, String birthday){
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        following = new ConcurrentLinkedQueue<>();
        followers = new ConcurrentLinkedQueue<>();
        posts = new ConcurrentLinkedQueue<>();
        privateMessages = new ConcurrentLinkedQueue<>();
        incomingPosts = new ConcurrentLinkedQueue<>();
        incomingPMs = new ConcurrentLinkedQueue<>();
        blocked = new ConcurrentLinkedQueue<>();
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

    public ConcurrentLinkedQueue<User> getFollowing() {
        return following;
    }

    public ConcurrentLinkedQueue<User> getFollowers() {
        return followers;
    }

    public ConcurrentLinkedQueue<String> getPosts() {
        return posts;
    }

    public ConcurrentLinkedQueue<PmMessage> getPrivateMessages() {
        return privateMessages;
    }

    public ConcurrentLinkedQueue<NotificationMessage> getIncomingPosts() {
        return incomingPosts;
    }

    public ConcurrentLinkedQueue<NotificationMessage> getIncomingPMs() {
        return incomingPMs;
    }

    public Integer getYearOfBirth(){
        String sYear = birthday.substring(6);
        Integer year = Integer.valueOf(sYear);
        return year;
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

    public boolean isBlocked(String user) {
        if (blocked.isEmpty()) {
        } else {
            for (User blockedUser : blocked) {
                if (blockedUser.getUsername() == user) {
                    return true;
                }
            }
        }
        return false;
    }

}
