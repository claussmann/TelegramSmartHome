package TelegramSmartHome.SmartHome.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Scanner;

public class Config {
    private String configFile = "config.conf"; //TODO:set location to default (/etc/TelegramSmartHome/config) except for developer

    private ConfigFile conf;

    public Config(){
        conf = new ConfigFile();
        String json=readConfigFile();
        ObjectMapper mapper = new ObjectMapper();
        try {
            conf = mapper.readValue(json, ConfigFile.class);
            System.out.println("Configuration loaded successfully");
        } catch (IOException e) {
            createNewConfig();
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
            System.out.println("No Config found!");
        }
        return ret;
    }

    private void createNewConfig() {
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
        conf.setBotToken(token);
        conf.setLastMessage(0);

        saveConfig();
    }

    private void saveConfig() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new FileOutputStream("config.conf"), conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully saved Configuration (see config.conf)");
    }

    public String getBotToken(){
        return conf.botToken;
    }

    public int getLastMessageID(){
        return conf.lastMessage;
    }

}
