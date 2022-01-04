package bgu.spl.net.api.bidi.commands;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
import bgu.spl.net.api.bidi.messages.AckMessage;
import bgu.spl.net.api.bidi.messages.ErrorMessage;
import bgu.spl.net.api.bidi.messages.PmMessage;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.UserRegistry;
import bgu.spl.net.srv.bidi.ConnectionsImpl;

public class PMCommand implements Command {
    private UserRegistry userRegistry;
    private PmMessage pmMessage;

    private String myUsername;
    private User myUser;
    private String otherUsername;
    private User otherUser;

    public PMCommand() {
        userRegistry = UserRegistry.getInstance();
    }

    @Override
    public void process(Message message, int connId, Connections connections) {
        pmMessage = (PmMessage) message;
        ConnectionsImpl conn = (ConnectionsImpl) connections;
        myUsername = conn.getUsername(connId);
        if (myUsername == null) {
            ErrorMessage error = new ErrorMessage(pmMessage.getOpCode());
            connections.send(connId, error); //error no such username is logged in
        }
        else {
            myUser = userRegistry.getUser(myUsername);
            otherUsername = pmMessage.getUsername();
            if (!userRegistry.isUserRegistered(otherUsername)) {
                ErrorMessage error = new ErrorMessage(pmMessage.getOpCode());
                connections.send(connId, error); //error other user is not registered
            }
            else {
                otherUser = userRegistry.getUser(otherUsername);
                if(!myUser.getFollowing().contains(otherUser)) {
                    ErrorMessage error = new ErrorMessage(pmMessage.getOpCode());
                    connections.send(connId, error); //error not following the recipient user
                }
                else {
                    //filter the message
                    myUser.addPM(pmMessage);
                    otherUser.addIncomingPM(pmMessage);

                    AckMessage ack = new AckMessage(pmMessage.getOpCode());
                    connections.send(connId, ack);
                }
            }
        }
    }
}
