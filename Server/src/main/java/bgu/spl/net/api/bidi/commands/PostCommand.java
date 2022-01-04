package bgu.spl.net.api.bidi.commands;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
import bgu.spl.net.api.bidi.messages.LoginMessage;
import bgu.spl.net.api.bidi.messages.NotificationMessage;
import bgu.spl.net.api.bidi.messages.PostMessage;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.UserRegistry;
import bgu.spl.net.srv.bidi.ConnectionsImpl;

import java.util.LinkedList;

public class PostCommand implements Command {

    private PostMessage currMessage = null;
    private boolean processed = false;
    private ConnectionsImpl connections = null;
    private LinkedList<String> usersToSend = null;
    private UserRegistry userRegistry;

    public PostCommand(){
        userRegistry = UserRegistry.getInstance();
    }

    @Override
    public boolean process(Message message, int connId, Connections connections) {
        this.currMessage = (PostMessage) message;
        this.connections = (ConnectionsImpl) connections;
        this.usersToSend = currMessage.getUsersToSend();
        String userName = this.connections.getUsername(connId);
        User currUser = userRegistry.getUser(userName);

        //send notification post to all following users and @user
        if(currUser.isLoggedIn()){
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
        }

        //send error message
        else{

        }
        return processed;
    }

    private void sendNotification(NotificationMessage notification, User receiver){
        int receiverId = connections.getId(receiver.getUsername());
        if(receiverId != -1){ //user is logged in
            this.connections.send(receiverId, notification);
        }

        else{ //user is logged out
            receiver.addIncomingPost(notification);
        }
    }
}
