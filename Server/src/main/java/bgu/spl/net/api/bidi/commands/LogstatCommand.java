package bgu.spl.net.api.bidi.commands;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.UserRegistry;
import bgu.spl.net.srv.bidi.ConnectionsImpl;

import java.util.LinkedList;

public class LogstatCommand implements Command {
    private UserRegistry userRegistry;
    private String myUsername;
    private short numOfPosts;
    private short numOfFollowers;
    private short numFollowing;


    public LogstatCommand() {
        userRegistry = UserRegistry.getInstance();
    }

    @Override
    public boolean process(Message message, int connId, Connections connections) {
        ConnectionsImpl conn = (ConnectionsImpl) connections;
        myUsername = conn.getUsername(connId);
        if (myUsername == null) {
            return false; //error no such username is logged in
        }
        LinkedList<User> users = userRegistry.getAllRegisteredUsers();
        for (User user: users) {
            if (user.isLoggedIn()){
                //calculate age (short)
                numOfPosts = (short) user.getPosts().size();
                numOfFollowers = (short) user.getFollowers().size();
                numFollowing = (short) user.getFollowing().size();
                //send with the ack the optional which is the 4 shorts above
            }
        }
        return true;
    }
}
