package bgu.spl.net.api.bidi.commands;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
import bgu.spl.net.api.bidi.messages.LoginMessage;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.UserRegistry;
import bgu.spl.net.srv.bidi.ConnectionsImpl;

public class LoginCommand implements Command {

    private LoginMessage currMessage = null;
    private boolean processed = false;
    private UserRegistry userRegistry;
    private ConnectionsImpl connections = null;


    public LoginCommand(){
        userRegistry = UserRegistry.getInstance();
   }

    @Override
    public boolean process(Message message, int connId, Connections connections) {
        currMessage = (LoginMessage) message;
        this.connections = (ConnectionsImpl) connections;
        String userName = ((LoginMessage) message).getUsername();
        if(userRegistry.isUserRegistered(userName)){
            User currUser = userRegistry.getUser(userName);

            //invalid user
            if(currUser.getPassword() != currMessage.getPassword() || currUser.isLoggedIn() ||
                currMessage.getCaptcha() == 0){
            }

            //user can log in
            else{
                ((ConnectionsImpl<?>) connections).addNewUser(userName, connId);
                currUser.setLoggedIn(true);
                processed = true;
            }
        }

        return processed;
    }
}
