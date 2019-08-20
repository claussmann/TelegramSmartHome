package TelegramSmartHome.SmartHome.Config;

import lombok.Data;

import java.util.Collection;

@Data
public class ConfigFile {
    String botToken;
    int lastMessage;
    Collection<Usergroup> usergroups;
}
