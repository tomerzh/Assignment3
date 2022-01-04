package bgu.spl.net.api.bidi.commands;

import bgu.spl.net.api.bidi.BGSBidiMessagingProtocol;
import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
import bgu.spl.net.api.bidi.messages.LoginMessage;
import bgu.spl.net.api.bidi.messages.LogoutMessage;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.UserRegistry;
import bgu.spl.net.srv.bidi.ConnectionHandler;
import bgu.spl.net.srv.bidi.ConnectionsImpl;

public class LogoutCommand implements Command {

    private boolean processed = false;
    private LogoutMessage currMessage = null;
    private UserRegistry userRegistry;
    private ConnectionsImpl connections = null;
    private BGSBidiMessagingProtocol protocol;

    public LogoutCommand(BGSBidiMessagingProtocol protocol){
       userRegistry = UserRegistry.getInstance();
       this.protocol = protocol;
    }

    @Override
    public boolean process(Message message, int connId, Connections connections)  {
        this.connections = (ConnectionsImpl) connections;
        this.currMessage = (LogoutMessage) message;
        String userName = this.connections.getUsername(connId);
        User currUser = userRegistry.getUser(userName);
        //check if connected, if true, logout user
        if(this.connections.getUsername(connId)!=null){
            currUser.setLoggedIn(false);
            //should be close after sending ack? to do!
            ((ConnectionsImpl<?>) connections).removeUser(userName);
            ((ConnectionsImpl<?>) connections).removeClient(connId);
            //send ack
            protocol.terminate();
        }

        else{
            //return error
        }

        return processed;
    }
}
