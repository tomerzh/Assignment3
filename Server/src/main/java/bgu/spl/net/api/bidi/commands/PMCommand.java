package bgu.spl.net.api.bidi.commands;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
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
    public boolean process(Message message, int connId, Connections connections) {
        pmMessage = (PmMessage) message;
        ConnectionsImpl conn = (ConnectionsImpl) connections;
        myUsername = conn.getUsername(connId);
        if (myUsername == null) {
            return false; //error no such username is logged in
        }
        myUser = userRegistry.getUser(myUsername);
        otherUsername = pmMessage.getUsername();
        if (!userRegistry.isUserRegistered(otherUsername)) {
            return false; //error other user is not registered
        }
        otherUser = userRegistry.getUser(otherUsername);
        if(!myUser.getFollowing().contains(otherUser)) {
            return false; //error not following the recipient user
        }

        //filter the message
        myUser.addPM(pmMessage);
        otherUser.addIncomingPM(pmMessage);
        return true;
    }
}
