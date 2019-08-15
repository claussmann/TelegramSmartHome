package TelegramSmartHome.TelegramIO.NewMessageHandler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(value = {"entities"})
public class Message {
    long message_id;
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

    public long getSenderId(){
        return from.id;
    }

}