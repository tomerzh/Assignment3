package bgu.spl.net.api.bidi.commands;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
import bgu.spl.net.api.bidi.messages.StatMessage;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.UserRegistry;
import bgu.spl.net.srv.bidi.ConnectionsImpl;

import java.util.LinkedList;

public class StatCommand implements Command {
    private UserRegistry userRegistry;
    private StatMessage statMessage;

    private String myUsername;
    private User currUser;
    private LinkedList<String> listOfUsernames;
    private LinkedList<AckCommand> ackCommands;

    private short numOfPosts;
    private short numOfFollowers;
    private short numFollowing;

    public StatCommand() {
        userRegistry = UserRegistry.getInstance();
    }

    @Override
    public boolean process(Message message, int connId, Connections connections) {
        statMessage = (StatMessage) message;
        ConnectionsImpl conn = (ConnectionsImpl) connections;
        myUsername = conn.getUsername(connId);
        if (myUsername == null) {
            return false; //error no such username is logged in
        }
        listOfUsernames = statMessage.getListOfUsernames();
        for (String username : listOfUsernames) {
            if (!userRegistry.isUserRegistered(username)){
                //return error and end the loop
            }
            currUser = userRegistry.getUser(username);
            //calculate age (short)
            numOfPosts = (short) currUser.getPosts().size();
            numOfFollowers = (short) currUser.getFollowers().size();
            numFollowing = (short) currUser.getFollowing().size();
            //create ack and add it to the list of acks
        }
        for (AckCommand ack : ackCommands) {
            //use ack function to send the ack
        }
        return true;
    }
}
