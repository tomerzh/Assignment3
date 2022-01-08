package bgu.spl.net.api.bidi.commands;
import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
import bgu.spl.net.api.bidi.messages.AckMessage;
import bgu.spl.net.api.bidi.messages.ErrorMessage;
import bgu.spl.net.api.bidi.messages.FollowMessage;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.UserRegistry;
import bgu.spl.net.srv.bidi.ConnectionsImpl;

public class FollowCommand implements Command {
    private UserRegistry userRegistry;
    private FollowMessage followMessage;
    private String myUsername;
    private User myUser;
    private boolean followOrUnfollow; //T for follow, F for unfollow
    private String otherUsername;
    private User otherUser;

    public FollowCommand() {
        userRegistry = UserRegistry.getInstance();
    }

    @Override
    public void process(Message message, int connId, Connections connections) {
        followMessage = (FollowMessage) message;
        ConnectionsImpl conn = (ConnectionsImpl) connections;
        myUsername = conn.getUsername(connId);
        if (myUsername == null) {
            ErrorMessage error = new ErrorMessage(followMessage.getOpCode());
            connections.send(connId, error); //error no such username is logged in
        }
        else {
            myUser = userRegistry.getUser(myUsername);
            followOrUnfollow = followMessage.isFollowOrUnfollow();
            otherUsername = followMessage.getUsername();
            otherUser = userRegistry.getUser(otherUsername);
            if (otherUser != null) {
                if(followOrUnfollow) { //for follow
                    if (myUser.getFollowing().contains(otherUser) || myUser.isBlocked(otherUser)) {
                        ErrorMessage error = new ErrorMessage(followMessage.getOpCode());
                        connections.send(connId, error); //error already following this user or this user is blocked
                    }
                    else {
                        myUser.addFollow(otherUser);
                        otherUser.addFollower(myUser);
                        AckMessage ack = new AckMessage(followMessage.getOpCode(), otherUsername);
                        connections.send(connId, ack);
                    }
                }
                else { //for unfollow
                    if (!myUser.getFollowing().contains(otherUser)) {
                        ErrorMessage error = new ErrorMessage(followMessage.getOpCode());
                        connections.send(connId, error); //error not following this user
                    }
                    else {
                        if (myUser.removeFollow(otherUser) && otherUser.removeFollower(myUser)) {
                            AckMessage ack = new AckMessage(followMessage.getOpCode(), otherUsername);
                            connections.send(connId, ack);
                        }
                        else {
                            ErrorMessage error = new ErrorMessage(followMessage.getOpCode());
                            connections.send(connId, error);
                        }
                    }
                }
            }
            else {
                ErrorMessage error = new ErrorMessage(followMessage.getOpCode());
                connections.send(connId, error); //other user is not registered
            }
        }
    }
}
