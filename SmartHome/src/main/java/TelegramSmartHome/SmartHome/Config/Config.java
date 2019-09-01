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
    private boolean createdNewConfig;

    public Config() {
        createdNewConfig = false;
        configUI = new ConfigUI();

        String json = Try.of(() -> readConfigFile()).getOrElse("");
        ObjectMapper mapper = new ObjectMapper();

        //Dieser Code verursacht die Erstellung einer neuen COnfig bei jedem Programmstart -> no idea why
        //der Json String wird korrekt eingelesen, sehr seltsam
        //conf = Try.of(() -> mapper.readValue(json, ConfigFile.class))
        //        .getOrElse(createNewConfig());

        try {
            conf = mapper.readValue(json, ConfigFile.class);
        } catch (IOException e) {
            conf = createNewConfig();
        }
    }

    //Constructor for future Tests -> Mock Injection
    public Config(ConfigFile configFile, ConfigUI configUI, boolean createdNewConfig) {
        this.createdNewConfig = createdNewConfig;
        this.configUI = configUI;
        this.conf = configFile;
    }


    public boolean isNewConfig() { return createdNewConfig;}

    public String getBotToken() {
        return conf.botToken;
    }

    public void updateBotToken(String newToken) {
        conf.setBotToken(newToken);
    }

    public long getLastMessageID() {
        return conf.lastMessage;
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

    private ConfigFile createNewConfig() {
        ConfigFile newConf = new ConfigFile();

        configUI.writeMessage("Creating new Config-File");
        configUI.writeMessage("Insert your Bot Token:");

        String token = configUI.readLine();
        newConf.setBotToken(token);
        newConf.setLastMessage(0);
        saveConfig(newConf);
        createdNewConfig = true;
        return newConf;
    }

    public  void saveConfig() {
        saveConfig(conf);
    }

    private void saveConfig(ConfigFile configFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new FileOutputStream("config.conf"), configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        configUI.writeMessage("Successfully saved Configuration (see "+this.configFile+")");
    }

}
