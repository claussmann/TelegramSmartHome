package TelegramSmartHome.SmartHome.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Scanner;

public class Config {
    private String configFile = "config.conf"; //TODO:set location to default (/etc/TelegramSmartHome/config) except for developer

    private ConfigFile conf;

    public Config(){
        conf = new ConfigFile();
        manageConfig();
    }

    private void manageConfig() {
        String json=readConfigFile();
        boolean newConfig = false;
        ObjectMapper mapper = new ObjectMapper();

        try {
            conf = mapper.readValue(json, ConfigFile.class);
            System.out.println("Configuration loaded successfully");
        } catch (IOException e) {
            createNewConfig();
            newConfig = true;
        }
        if(!newConfig) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Edit Config?(y/n)");
            String answer = "n";
            try {
                answer = br.readLine();
                answer = answer.toLowerCase();
                if(answer.length()>1 || !(answer.equals("y") || answer.equals("n"))) {
                    throw new IllegalArgumentException("Input not y or n");
                }
            } catch (IOException e) {
                System.out.println("Invalid answer");
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            if(answer.equals("y")){
                editConfig();
            }

        }

    }

    private void editConfig() {
        System.out.println("Current Bot Token:");
        System.out.println(conf.botToken);
        System.out.println("New Bot Token:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String token = br.readLine();
            conf.setBotToken(token);
            System.out.println("New token saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveConfig();
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
