package TelegramSmartHome.SmartHome.Config;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class Usergroup {
    Collection<String> members;
    String groupname;

    //for jackson
    public Usergroup(){}

    public Usergroup(String name){
        groupname = name;
        members = new ArrayList<>();
    }

    public boolean containsMember(String username) {
        return members.contains(username);
    }

    public void addMember(String username){
        members.add(username);
    }
}
