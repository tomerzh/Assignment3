package bgu.spl.net.api.bidi.commands;

import bgu.spl.net.api.bidi.BGSBidiMessagingProtocol;
import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
import bgu.spl.net.api.bidi.messages.AckMessage;
import bgu.spl.net.api.bidi.messages.ErrorMessage;
import bgu.spl.net.api.bidi.messages.LogoutMessage;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.UserRegistry;
import bgu.spl.net.srv.bidi.ConnectionsImpl;

public class LogoutCommand implements Command {

    private boolean processed = false;
    private LogoutMessage currMessage = null;
    private UserRegistry userRegistry;
    private ConnectionsImpl connections = null;


    public LogoutCommand(){
       userRegistry = UserRegistry.getInstance();
    }

    @Override
    public void process(Message message, int connId, Connections connections)  {
        this.connections = (ConnectionsImpl) connections;
        this.currMessage = (LogoutMessage) message;
        System.out.println("Logout connId is: " + connId);
        String userName = this.connections.getUsername(connId);
        User currUser = userRegistry.getUser(userName);

        //check if connected, if true, logout user
        if(userName != null){
            currUser.setLoggedIn(false);

            this.connections.disconnect(connId);
            this.connections.removeUser(userName);
            this.connections.removeClient(connId);

            AckMessage ack = new AckMessage(currMessage.getOpCode());
            this.connections.send(connId, ack);
            System.out.println("Logout ack sent!");
        }
        else{
            ErrorMessage error = new ErrorMessage(currMessage.getOpCode());
            this.connections.send(connId, error);
        }
    }
}
