package bgu.spl.net.srv;

import java.util.LinkedList;
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

    public LinkedList<User> getAllRegisteredUsers() {
        return new LinkedList<>(usernameToUser.values());
    }

    public void addRegistry(String username, User user){
        usernameToUser.put(username, user);
    }

    public void removeRegistry(String username){
        usernameToUser.remove(username);
    }

    public boolean isUserRegistered(String username) {
        return usernameToUser.containsKey(username);
    }

    public User getUser(String userName){
        if (!usernameToUser.containsKey(userName)) {
            return null;
        }
        return usernameToUser.get(userName);
    }
}
