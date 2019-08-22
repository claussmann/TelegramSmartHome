package TelegramSmartHome.SmartHome.Config;

public class ConfigService {

    private Config config;

    public ConfigService() {
        config = new Config();
    }

    public String getBotToken() { return config.getBotToken();}

    public long getLastMessageId() { return config.getLastMessageID();}
}
