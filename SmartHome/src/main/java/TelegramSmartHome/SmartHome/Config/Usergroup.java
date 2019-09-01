package TelegramSmartHome.SmartHome.Config;

import lombok.Data;

import java.util.Collection;

@Data
public class Usergroup {
    Collection<String> members;
    String groupname;

    public boolean containsMember(String username) {
        return members.contains(username);
    }
}
