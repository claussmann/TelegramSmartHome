package TelegramSmartHome.TelegramIO;

import java.util.ArrayList;
import java.util.List;

public class UpdateService {
    private String token;
    private int messageOffset;
    List<IMessageEvaluator> evaluators;

    public UpdateService(String botToken){
        this.token=botToken;
        evaluators = new ArrayList<>();
    }

    public UpdateService(String botToken, int messageOffset){
        this.token=botToken;
        this.messageOffset=messageOffset;
        evaluators = new ArrayList<>();

    }

    /**
     * Adds an Evaluator to the Queue.
     * All Evaluators will receive the updates from Telegram once the start() method is called.
     * @param evaluator
     */
    public void addUpdateListener(IMessageEvaluator evaluator){
        evaluators.add(evaluator);
    }

    /**
     * Starts listening for updates. Notifies all registered IMessageEvaluators.
     * Use addUpdateListener to add an Evaluator.
     */
    public void start(){
        String url = "https://api.telegram.org/bot" + token + "/getUpdates";
        //while true
        //  send request
        //  parse to Message
        //  if(message is new)
        //      config.updateLatestMessage(message.id)
        //      foreach(evaluator in evaluators):
        //          evaluator.processMessage(message)
        //  sleep
    }
}
