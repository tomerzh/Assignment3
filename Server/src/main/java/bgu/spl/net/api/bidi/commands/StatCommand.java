package bgu.spl.net.api.bidi.commands;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
import bgu.spl.net.api.bidi.messages.AckMessage;
import bgu.spl.net.api.bidi.messages.ErrorMessage;
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
    private LinkedList<AckMessage> ackCommands;

    private short age;
    private short numOfPosts;
    private short numOfFollowers;
    private short numFollowing;

    public StatCommand() {
        userRegistry = UserRegistry.getInstance();
    }

    @Override
    public void process(Message message, int connId, Connections connections) {
        statMessage = (StatMessage) message;
        ConnectionsImpl conn = (ConnectionsImpl) connections;
        myUsername = conn.getUsername(connId);
        if (myUsername == null) {
            ErrorMessage error = new ErrorMessage(statMessage.getOpCode());
            connections.send(connId, error); //error no such username is logged in
        }
        else {
            listOfUsernames = statMessage.getListOfUsernames();
            User myUser = userRegistry.getUser(myUsername);
            for (String username : listOfUsernames) {
                //send error if user in list not exists or blocked
                if (!userRegistry.isUserRegistered(username) || myUser.isBlocked(username)){
                    ErrorMessage error = new ErrorMessage(statMessage.getOpCode());
                    connections.send(connId, error);
                    break;
                }
                else {
                    currUser = userRegistry.getUser(username);
                    //calculate age (short)
                    numOfPosts = (short) currUser.getPosts().size();
                    numOfFollowers = (short) currUser.getFollowers().size();
                    numFollowing = (short) currUser.getFollowing().size();
                    AckMessage ack = new AckMessage(statMessage.getOpCode(),
                            age, numOfPosts, numOfFollowers, numFollowing);
                    ackCommands.add(ack);
                }
            }
            for (AckMessage ack : ackCommands) {
                connections.send(connId, ack);
            }
        }
    }
}
