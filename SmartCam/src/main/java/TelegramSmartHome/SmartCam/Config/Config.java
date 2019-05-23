package TelegramSmartHome.SmartCam.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Config {
    String configFile="config.conf"; //TODO:set location to default (/etc/TelegramSmartHome/config) except for developer

    ConfigFile conf;

    public Config(){
        String json=readConfigFile();
        ObjectMapper mapper = new ObjectMapper();
        try {
            conf = mapper.readValue(json, ConfigFile.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readConfigFile(){
        BufferedReader reader;
        String ret="";
        try {
            reader = new BufferedReader(new FileReader(configFile));
            String line;
            while((line = reader.readLine())!=null){
                ret+=line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String getBotToken(){
        return conf.botToken;
    }

}
