package TelegramSmartHome.SmartHome.Config;

import TelegramSmartHome.SmartHome.SmartHomeApplication;
import TelegramSmartHome.SmartHome.UserManagement.UserService;
import TelegramSmartHome.TelegramIO.MessageSendService;
import TelegramSmartHome.TelegramIO.UpdateService;
import TelegramSmartHome.TelegramIO.message.Message;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConfigService {

    private UpdateService updateService;
    @Setter
    private MessageSendService sendService;
    @Setter
    private UserService userService;
    private Config config;
    private List<ConfigCache> caches;

    public ConfigService() {
        config = new Config();
        caches = new ArrayList<>();
    }

    //Constructor for tests
    public ConfigService(Config config, ConfigUI configUI) {
        this.config = config;
        caches = new ArrayList<>();
    }

    public void setUpdateService(UpdateService updateService){
        this.updateService = updateService;
        updateService.addUpdateListener(this::processMessage);
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

    public void setLastMessageId(long id){
        config.updateLastMessageID(id);
    }

    public Collection<String> groupsOfUser(String username) {
        return config.groupsOfUser(username);
    }

    public void processMessage(Message message) {
        if(message.getMessageText().startsWith("/bottoken")){
            if(userService.memberOf(message.getSenderUsername(), "administrators")) {
                sendService.sendMessage(message.getSenderId(), "Updated. Restarting!");
                String token = message.getMessageText().replace("/bottoken ", "");
                config.updateBotToken(token);
                config.updateLastMessageID(0);
                SmartHomeApplication.restartApplication();
            }
            else sendService.sendMessage(message.getSenderId(), "You are no admin.");
        }
    }
}
