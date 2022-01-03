package bgu.spl.net.api.bidi.commands;
import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
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
    public boolean process(Message message, int connId, Connections connections) {
        followMessage = (FollowMessage) message;
        ConnectionsImpl conn = (ConnectionsImpl) connections;
        myUsername = conn.getUsername(connId);
        if (myUsername == null) {
            return false; //error no such username is logged in
        }
        myUser = userRegistry.getUser(myUsername);
        followOrUnfollow = followMessage.isFollowOrUnfollow();
        otherUsername = followMessage.getUsername();
        otherUser = userRegistry.getUser(otherUsername);
        if(followOrUnfollow) { //for follow
            if (myUser.getFollowing().contains(otherUser)) {
                return false; //error already following this user
            }
            myUser.addFollow(otherUser);
            otherUser.addFollower(myUser);
            return true;
        }
        else { //for unfollow
            if (!myUser.getFollowing().contains(otherUser)) {
                return false; //error not following this user
            }
            return myUser.removeFollow(otherUser) && otherUser.removeFollower(myUser); //error
        }
    }
}
