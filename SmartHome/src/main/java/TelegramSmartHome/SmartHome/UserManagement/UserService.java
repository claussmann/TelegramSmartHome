package TelegramSmartHome.SmartHome.UserManagement;

import TelegramSmartHome.SmartHome.Config.Config;
import java.util.HashMap;

public class UserService {

    HashMap<String, User> users;

    public UserService(Config conf) {
        users = new HashMap<>();
    }

    public boolean memberOf(String username, String groupId) {
        return users.getOrDefault(username, new User("default")).isMemberOf(groupId);
    }
}
