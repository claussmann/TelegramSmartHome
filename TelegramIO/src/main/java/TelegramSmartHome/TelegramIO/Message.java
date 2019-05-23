package TelegramSmartHome.TelegramIO;

import lombok.Data;

@Data
public class Message {
    int message_id;
    User from;
    Chat chat;
    int date;
    String text;

    public String getMessageText(){
        return text;
    }

    public String getSenderUsername(){
        return from.username;
    }

    public String getSenderName(){
        return from.first_name;
    }

    public int getSenderId(){
        return from.id;
    }

}
