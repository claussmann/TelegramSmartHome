package TelegramSmartHome.SmartHome.Config;

import TelegramSmartHome.SmartHome.UserManagement.UserService;
import TelegramSmartHome.TelegramIO.IMessageEvaluator;
import TelegramSmartHome.TelegramIO.MessageSendService;
import TelegramSmartHome.TelegramIO.UpdateService;
import TelegramSmartHome.TelegramIO.message.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConfigService implements IMessageEvaluator {

    private UpdateService updateService;
    private MessageSendService sendService;
    private UserService userService;
    private Config config;
    private List<ConfigCache> caches;

    public ConfigService(UpdateService updateService, MessageSendService sendService, UserService userService) {
        this.updateService = updateService;
        this.sendService = sendService;
        this.userService = userService;
        config = new Config();
        caches = new ArrayList<>();
    }

    //Constructor for tests
    public ConfigService(Config config, ConfigUI configUI) {
        this.config = config;
        caches = new ArrayList<>();
    }

    public void editBotToken(String newToken) {
        config.updateBotToken(newToken);
        config.save();

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

    @Override
    public void processMessage(Message message) {
        if(message.getMessageText().startsWith("/bottoken")){
            if(userService.memberOf(message.getSenderUsername(), "administrators")) {
                sendService.sendMessage(message.getSenderId(), "Updated. Restarting!");
                config.updateBotToken(message.getMessageText());
                config.saveConfig();
                //TODO: Reboot
            }
            else sendService.sendMessage(message.getSenderId(), "You are no admin.");
        }
    }
}
