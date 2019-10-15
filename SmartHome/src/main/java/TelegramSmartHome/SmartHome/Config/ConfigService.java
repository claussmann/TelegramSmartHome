package TelegramSmartHome.SmartHome.Config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConfigService {

    private Config config;
    private ConfigUI configUI;
    private List<ConfigCache> caches;

    public ConfigService() {
        config = new Config();
        configUI = new ConfigUI();
        caches = new ArrayList<>();
    }

    //Constructor for tests
    public ConfigService(Config config, ConfigUI configUI) {
        this.configUI = configUI;
        this.config = config;
        caches = new ArrayList<>();
    }

    public void editBotToken(String newToken) {
        config.updateBotToken(newToken);
        configUI.writeMessage("New token saved");
        config.saveConfig();

    }

    public void registerCache(ConfigCache c) {
        caches.add(c);
    }

    public String getBotToken() {
        return config.getBotToken();
    }

    public long getLastMessageId() {
        return config.getLastMessageID();
    }

    public Collection<String> groupsOfUser(String username) {
        return config.groupsOfUser(username);
    }
}
