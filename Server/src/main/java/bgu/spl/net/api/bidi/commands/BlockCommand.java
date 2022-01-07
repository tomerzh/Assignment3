package bgu.spl.net.api.bidi.commands;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
import bgu.spl.net.api.bidi.messages.AckMessage;
import bgu.spl.net.api.bidi.messages.BlockMessage;
import bgu.spl.net.api.bidi.messages.ErrorMessage;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.UserRegistry;
import bgu.spl.net.srv.bidi.ConnectionsImpl;

import java.util.LinkedList;

public class BlockCommand implements Command {

    private BlockMessage currMessage = null;
    private ConnectionsImpl connections = null;
    private UserRegistry userRegistry;

    public BlockCommand(){
        userRegistry = UserRegistry.getInstance();
    }

    @Override
    public void process(Message message, int connId, Connections connections) {
        currMessage = (BlockMessage) message;
        this.connections = (ConnectionsImpl) connections;
        String currUserName = this.connections.getUsername(connId);
        if(currUserName != null){
            User currUser = userRegistry.getUser(currUserName);
            User blockedUser = userRegistry.getUser(currMessage.getUsername());
            if(blockedUser != null){
                //stop follow each other
                currUser.removeFollow(blockedUser);
                currUser.removeFollower(blockedUser);
                blockedUser.removeFollow(currUser);
                blockedUser.removeFollower(currUser);
                //blocking each other
                currUser.addBlock(blockedUser);
                blockedUser.addBlock(currUser);

                AckMessage ack = new AckMessage(currMessage.getOpCode());
                this.connections.send(connId, ack);
            }

            else{
                ErrorMessage error = new ErrorMessage(currMessage.getOpCode());
                this.connections.send(connId, error);
            }
        }

        else{
            ErrorMessage error = new ErrorMessage(currMessage.getOpCode());
            this.connections.send(connId, error);
        }

    }
}
