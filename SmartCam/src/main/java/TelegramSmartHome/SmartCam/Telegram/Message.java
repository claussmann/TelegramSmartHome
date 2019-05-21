package TelegramSmartHome.SmartCam.Telegram;

import lombok.Data;

@Data
public class Message {
    int message_id;
    User from;
    Chat chat;
    int date;
    String text;

}
