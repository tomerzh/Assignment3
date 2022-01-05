package bgu.spl.net.api.bidi.commands;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
import bgu.spl.net.api.bidi.messages.*;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.UserRegistry;
import bgu.spl.net.srv.bidi.ConnectionsImpl;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class PostCommand implements Command {

    private PostMessage currMessage = null;
    private ConnectionsImpl connections = null;
    private LinkedList<String> usersToSend = null;
    private UserRegistry userRegistry;

    public PostCommand(){
        userRegistry = UserRegistry.getInstance();
    }

    @Override
    public void process(Message message, int connId, Connections connections) {
        this.currMessage = (PostMessage) message;
        this.connections = (ConnectionsImpl) connections;
        this.usersToSend = currMessage.getUsersToSend();
        String userName = this.connections.getUsername(connId);
        User currUser = userRegistry.getUser(userName);

        if(currUser.isLoggedIn()){
            boolean err = false;
            //checks if @user is blocked user or unregistered
            for(String name : usersToSend){
                if(currUser.isBlocked(name) || !(userRegistry.isUserRegistered(name))){
                    err = true;
                }
            }

            if(!err){
                //add to myPosts
                currUser.newPost(currMessage.getContent());
                //send to followers
                for(User follower : currUser.getFollowers()){
                    //send notification
                    NotificationMessage notification = new NotificationMessage((byte) 1, userName,
                            currMessage.getContent());
                    sendNotification(notification, follower);
                }
                //send to @users
                for(String name : usersToSend){
                    User receiver = userRegistry.getUser(name);
                    NotificationMessage notification = new NotificationMessage((byte) 1, userName,
                            currMessage.getContent());
                    sendNotification(notification, receiver);
                }

                AckMessage ack = new AckMessage(currMessage.getOpCode());
                this.connections.send(connId, ack);
            }

            else{
                ErrorMessage error = new ErrorMessage(currMessage.getOpCode());
                this.connections.send(connId, error);
            }
        }

        //send error message
        else{
            ErrorMessage error = new ErrorMessage(currMessage.getOpCode());
            connections.send(connId, error);
        }
    }

    private void sendNotification(NotificationMessage notification, User receiver){
        int receiverId = connections.getId(receiver.getUsername());
        if(receiverId != -1){ //user is logged in
            connections.send(receiverId, notification);
        }

        else{ //user is logged out
            receiver.addIncomingPost(notification);
        }
    }
}
