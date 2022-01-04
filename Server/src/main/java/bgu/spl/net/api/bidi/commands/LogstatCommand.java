package bgu.spl.net.api.bidi.commands;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
import bgu.spl.net.api.bidi.messages.AckMessage;
import bgu.spl.net.api.bidi.messages.ErrorMessage;
import bgu.spl.net.api.bidi.messages.LogstatMessage;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.UserRegistry;
import bgu.spl.net.srv.bidi.ConnectionsImpl;

import java.util.LinkedList;

public class LogstatCommand implements Command {
    private UserRegistry userRegistry;
    private LogstatMessage logstatMessage;
    private String myUsername;

    private short age;
    private short numOfPosts;
    private short numOfFollowers;
    private short numFollowing;


    public LogstatCommand() {
        userRegistry = UserRegistry.getInstance();
    }

    @Override
    public void process(Message message, int connId, Connections connections) {
        logstatMessage = (LogstatMessage) message;
        ConnectionsImpl conn = (ConnectionsImpl) connections;
        myUsername = conn.getUsername(connId);
        if (myUsername == null) {
            ErrorMessage error = new ErrorMessage(logstatMessage.getOpCode());
            connections.send(connId, error); //error no such username is logged in
        }
        else {
            LinkedList<User> users = userRegistry.getAllRegisteredUsers();
            for (User user: users) {
                if (user.isLoggedIn()){
                    //calculate age (short)
                    numOfPosts = (short) user.getPosts().size();
                    numOfFollowers = (short) user.getFollowers().size();
                    numFollowing = (short) user.getFollowing().size();

                    AckMessage ack = new AckMessage(logstatMessage.getOpCode(),
                            age, numOfPosts, numOfFollowers, numFollowing);
                    connections.send(connId, ack);
                }
            }
        }
    }
}
