package TelegramSmartHome.SmartHome.UserManagement;

import java.util.ArrayList;
import java.util.Collection;

public class User {

    String username;
    Collection<String> groupmemberships;

    public User(String name){
        username = name;
        groupmemberships = new ArrayList<>();
    }

    public void addToGroup(String groupId){
        groupmemberships.add(groupId.toLowerCase());
    }

    public void removeFromGroup(String groupId){
        while(groupmemberships.remove(groupId.toLowerCase()));
    }

    public boolean isMemberOf(String groupId) {
        return groupmemberships.contains(groupId.toLowerCase());
    }
}
