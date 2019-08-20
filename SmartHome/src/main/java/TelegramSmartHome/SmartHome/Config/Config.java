package TelegramSmartHome.SmartHome.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class Config {
    private String configFile = "config.conf"; //TODO:set location to default (/etc/TelegramSmartHome/config) except for developer

    private ConfigFile conf;

    public Config() {
        String json = Try.of(() -> readConfigFile()).getOrElse("");
        ObjectMapper mapper = new ObjectMapper();
        conf = Try.of(() -> mapper.readValue(json, ConfigFile.class))
                .getOrElse(createNewConfig());
    }


    public String getBotToken() {
        return conf.botToken;
    }

    public int getLastMessageID() {
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
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Creating new Config-File");
        System.out.println("Insert your Bot Token:");
        String token = null;
        try {
            token = br.readLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        newConf.setBotToken(token);
        newConf.setLastMessage(0);
        saveConfig(newConf);
        return newConf;
    }

    private void saveConfig() {
        saveConfig(conf);
    }

    private void saveConfig(ConfigFile configFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new FileOutputStream("config.conf"), configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully saved Configuration (see "+this.configFile+")");
    }

}
