package TelegramSmartHome.SmartHome.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class Config {
    private String configFile = "config.conf"; //TODO:set location to default (/etc/TelegramSmartHome/config) except for developer

    private ConfigFile conf;
    private ConfigUI configUI;

    public Config() {
        configUI = new ConfigUI();

        String json = Try.of(() -> readConfigFile()).getOrElse("");
        ObjectMapper mapper = new ObjectMapper();

        try {
            conf = mapper.readValue(json, ConfigFile.class);
        } catch (IOException e) {
            System.out.println("The Config does not exist or is invalid.");
            createNewConfig();
        }
    }

    //Constructor for future Tests -> Mock Injection
    public Config(ConfigFile configFile, ConfigUI configUI, boolean createdNewConfig) {
        this.configUI = configUI;
        this.conf = configFile;
    }

    public String getBotToken() {
        return conf.botToken;
    }

    public void updateBotToken(String newToken) {
        conf.setBotToken(newToken);
        save();
    }

    public long getLastMessageID() {
        return conf.lastMessage;
    }

    public void updateLastMessageID(long id){
        conf.setLastMessage(id);
        save();
    }

    public Collection<String> groupsOfUser(String username){
        Collection<String> groups = new ArrayList<>();
        for(Usergroup group : conf.getUsergroups()){
            if(group.containsMember(username)){
                groups.add(group.getGroupname());
            }
        }
        return groups;
    }

    private String readConfigFile() throws Exception {
        String ret = "";
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        String line;
        while ((line = reader.readLine()) != null) {
            ret += line;
        }
        return ret;
    }

    private void createNewConfig() {
        conf = new ConfigFile();

        configUI.writeMessage("Creating new Config-File\nInsert your Bot Token:");
        String token = configUI.readLine();

        configUI.writeMessage("Enter a username as the first admin:");
        String admin = configUI.readLine();

        conf.setBotToken(token);
        conf.setLastMessage(0);
        conf.addUserToGroup(admin, "administrators");
        save();
    }

    private void save() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new FileOutputStream("config.conf"), conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        configUI.writeMessage("Successfully saved Configuration (see "+this.conf+")");
    }

}
