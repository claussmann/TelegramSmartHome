package TelegramSmartHome.SmartHome.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConfigUI {

    private BufferedReader stdInReader;

    public ConfigUI() {
        stdInReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void writeMessage(String message) {
        System.out.println(message);
    }

    public String readLine() {
        String input;
        try {
            input = stdInReader.readLine();
        } catch (IOException e) {
            input = "";
            e.printStackTrace();
        }
        return input;
    }
}
