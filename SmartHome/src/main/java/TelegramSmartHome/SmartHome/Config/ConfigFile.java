package TelegramSmartHome.SmartHome.Config;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class ConfigFile {
    String botToken;
    long lastMessage;
    Collection<Usergroup> usergroups;

    public ConfigFile(){
        usergroups = new ArrayList<>();
    }

    public void addUserToGroup(String username, String groupname){
        Usergroup group = null;
        for(Usergroup current : usergroups){
            if(current.getGroupname().equals(groupname)){
                group = current;
                break;
            }
        }
        if(group == null){
            group = new Usergroup(groupname);
            group.addMember(username);
            usergroups.add(group);
        }
        else {
            group.addMember(username);
        }
    }
}
