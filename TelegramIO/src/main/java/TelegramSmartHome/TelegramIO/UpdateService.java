package TelegramSmartHome.TelegramIO;

public class UpdateService {
    private String token;
    private int messageOffset;

    public UpdateService(String botToken){
        this.token=botToken;
    }

    public UpdateService(String botToken, int messageOffset){
        this.token=botToken;
        this.messageOffset=messageOffset;
    }

    public void listenForUpdates(IMessageEvaluator evaluator){
        String url = "https://api.telegram.org/bot" + token + "/getUpdates";
        //while true
        //  send request
        //  parse to Message
        //  if(message is new)
        //      config.updateLatestMessage(message.id)
        //      evaluator.processMessage(message)
        //  sleep
    }
}
