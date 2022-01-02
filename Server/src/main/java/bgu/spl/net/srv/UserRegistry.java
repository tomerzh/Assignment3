package bgu.spl.net.srv;

import java.util.concurrent.ConcurrentHashMap;

public class UserRegistry {
    private ConcurrentHashMap<String, User> usernameToUser;
    private static UserRegistry instance = new UserRegistry();

    private UserRegistry(){
        usernameToUser = new ConcurrentHashMap<>();
    }

    public static UserRegistry getInstance() {
        return instance;
    }

    public void addRegistry(User user){
        usernameToUser.put(user.getUsername(), user);
    }

    public void removeRegistry(String username){
        usernameToUser.remove(username);
    }
}
