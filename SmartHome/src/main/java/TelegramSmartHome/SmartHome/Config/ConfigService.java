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
    private ConfigUI configUI;
    private List<ConfigCache> caches;

    public ConfigService(UpdateService updateService, MessageSendService sendService, UserService userService) {
        this.updateService = updateService;
        this.sendService = sendService;
        this.userService = userService;
        config = new Config();
        configUI = new ConfigUI();
        caches = new ArrayList<>();
        editBotToken();
    }

    //Constructor for tests
    public ConfigService(Config config, ConfigUI configUI) {
        this.configUI = configUI;
        this.config = config;
        caches = new ArrayList<>();
        editBotToken();
    }

    private void editBotToken() {
        if(!config.isNewConfig()) {
            configUI.writeMessage("Edit Bottoken (y/n)?");
            String answer = configUI.readLine();
            answer = answer.toLowerCase();
            try {
                if(answer.length()>1 || !(answer.equals("y") || answer.equals("n"))) {
                    throw new IllegalArgumentException("Input not y or n");
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            if(answer.equals("y")){
                configUI.writeMessage("Current Bot Token:");
                configUI.writeMessage(config.getBotToken());
                configUI.writeMessage("New Bot Token:");

                String token = configUI.readLine();
                config.updateBotToken(token);
                configUI.writeMessage("New token saved");

                config.saveConfig();
            }
        }
    }

    public void registerCache(ConfigCache c){
        caches.add(c);
    }

    public String getBotToken() { return config.getBotToken();}

    public long getLastMessageId() { return config.getLastMessageID();}

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
