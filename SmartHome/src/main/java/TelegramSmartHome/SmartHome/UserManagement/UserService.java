package TelegramSmartHome.SmartHome.UserManagement;

import TelegramSmartHome.SmartHome.Config.ConfigService;

import java.util.HashMap;

public class UserService {

    HashMap<String, User> users;
    ConfigService config;

    public UserService(ConfigService conf) {
        users = new HashMap<>();
        config = conf;
    }

    public boolean memberOf(String username, String groupId) {
        //If User is looked-up the first time, Userservice looks in config file.
        //Next time it is faster. This practise saves time due to the format of the config file.
        //It is group -> user
        //but we need user -> groups
        //Implementing a Config.getAllUsers() method will be complicated due to this design.

        //TODO: What if config changes? New Users could come any time... what if they already exist but without rights?
        if(users.get(username) == null){
            User user = new User(username);
            for(String group : config.groupsOfUser(username)){
                user.addToGroup(group);
            }
            users.put(username, user);
        }
        return users.get(username).isMemberOf(groupId);
    }
}
