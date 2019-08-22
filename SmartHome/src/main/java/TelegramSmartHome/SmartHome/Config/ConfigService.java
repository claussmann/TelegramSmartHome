package TelegramSmartHome.SmartHome.Config;

public class ConfigService {

    private Config config;
    private ConfigUI configUI;

    public ConfigService() {
        config = new Config();
        configUI = new ConfigUI();
        editBotToken();
    }

    //Constructor for tests
    public ConfigService(Config config, ConfigUI configUI) {
        this.configUI = configUI;
        this.config = config;
        editBotToken();
    }

    private void editBotToken() {
        if(!config.isNewConfig()) {
            configUI.writeMessage("Edit Config");
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

    public String getBotToken() { return config.getBotToken();}

    public long getLastMessageId() { return config.getLastMessageID();}
}
