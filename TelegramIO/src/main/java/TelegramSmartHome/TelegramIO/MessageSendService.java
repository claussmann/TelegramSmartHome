package TelegramSmartHome.TelegramIO;

public class MessageSendService {

    private String token;

    public MessageSendService(String botToken){
        this.token = botToken;
    }

    public void sendMessage(String recipientUserId, String messageText){
        String url = "https://api.telegram.org/bot" + token +
                "/sendMessage?chat_id=" + recipientUserId +
                "&text=" + messageText;
        //call url
    }
}
