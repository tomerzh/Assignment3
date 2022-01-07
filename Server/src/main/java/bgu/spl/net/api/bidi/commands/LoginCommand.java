package bgu.spl.net.api.bidi.commands;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
import bgu.spl.net.api.bidi.messages.AckMessage;
import bgu.spl.net.api.bidi.messages.ErrorMessage;
import bgu.spl.net.api.bidi.messages.LoginMessage;
import bgu.spl.net.api.bidi.messages.NotificationMessage;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.UserRegistry;
import bgu.spl.net.srv.bidi.ConnectionsImpl;

public class LoginCommand implements Command {

    private LoginMessage currMessage = null;
    private UserRegistry userRegistry;


    public LoginCommand(){
        userRegistry = UserRegistry.getInstance();
   }

    @Override
    public void process(Message message, int connId, Connections connections) {
        currMessage = (LoginMessage) message;
        ConnectionsImpl connectionsImpl = (ConnectionsImpl) connections;
        String userName = currMessage.getUsername();
        if(userRegistry.isUserRegistered(userName)){
            User currUser = userRegistry.getUser(userName);

            //invalid user
            if(!currUser.getPassword().equals(currMessage.getPassword())|| currUser.isLoggedIn() ||
                currMessage.getCaptcha() == 0){
                ErrorMessage error = new ErrorMessage(currMessage.getOpCode());
                connectionsImpl.send(connId, error);
            }
            //user can log in
            else{
                connectionsImpl.addNewUser(userName, connId);
                currUser.setLoggedIn(true);
                AckMessage ack = new AckMessage(currMessage.getOpCode());
                connectionsImpl.send(connId, ack);

                //send to user notifications for unseen posts
                for(NotificationMessage notification : currUser.getIncomingPosts()){
                    connectionsImpl.send(connId, notification);
                }

                //send to user notifications for unseen pm
                for(NotificationMessage notification : currUser.getIncomingPMs()){
                    connectionsImpl.send(connId, notification);
                }
                currUser.clearPostAndPm();
            }
        }
        else {
            ErrorMessage error = new ErrorMessage(currMessage.getOpCode());
            connectionsImpl.send(connId, error);
        }
    }
}
