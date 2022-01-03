package bgu.spl.net.api.bidi.commands;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.Message;
import bgu.spl.net.api.bidi.messages.LoginMessage;

public class LoginCommand implements Command {

    private LoginMessage currMessage = null;
    boolean processed = false;

    public LoginCommand(){

    }

    @Override
    public boolean process(Message message, int connId, Connections connections) {
        currMessage = (LoginMessage) message;


        return processed;
    }
}
