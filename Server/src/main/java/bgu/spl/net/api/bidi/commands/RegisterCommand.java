package bgu.spl.net.api.bidi.commands;

import bgu.spl.net.api.bidi.Command;
import bgu.spl.net.api.bidi.Message;
import bgu.spl.net.api.bidi.messages.RegisterMessage;
import bgu.spl.net.srv.User;
import bgu.spl.net.srv.UserRegistry;

public class RegisterCommand implements Command {
    private UserRegistry userRegistry;
    private RegisterMessage registerMessage;
    private String username;
    private String password;
    private String birthday;

    public RegisterCommand() {
        userRegistry = UserRegistry.getInstance();
    }

    @Override
    public boolean process(Message message) {
        registerMessage = (RegisterMessage) message;
        username = registerMessage.getUsername();

        if (userRegistry.isUserRegistered(username)) {
            return false; //error, user already registered
        }
        else {
            password = registerMessage.getPassword();
            birthday = registerMessage.getBirthday();
            User user = new User(username, password, birthday);
            userRegistry.addRegistry(username, user);
            return true;
        }
    }
}
