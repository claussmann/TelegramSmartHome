package TelegramSmartHome.TelegramIO;

import TelegramSmartHome.TelegramIO.apicom.JsonHandler;
import TelegramSmartHome.TelegramIO.message.Message;
import TelegramSmartHome.TelegramIO.message.Update;
import io.vavr.control.Try;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UpdateService {
    private long lastUpdateId;
    List<Consumer<Message>> evaluators;
    private JsonHandler jsonHandler;

    public UpdateService(long lastUpdateId, JsonHandler jsonHandler){
        this.lastUpdateId = lastUpdateId;
        this.jsonHandler = jsonHandler;
        evaluators = new ArrayList<>();
    }

    public long getLastUpdateId(){
        return lastUpdateId;
    }

    /**
     * Adds an Evaluator to the Queue.
     * All Evaluators will receive the updates from Telegram once the start() method is called.
     * @param evaluator
     */
    public void addUpdateListener(Consumer<Message> evaluator){
        evaluators.add(evaluator);
    }

    /**
     * Starts listening for updates. Notifies all registered IMessageEvaluators.
     * Use addUpdateListener to add an Evaluator.
     */
    public void start(){
        while (true){
            getUpdates();
            Try.of(() -> {Thread.sleep(10000); return null;});
        }
    }

    public long getUpdates(){
        List<Update> newMessages = jsonHandler.getNewMessages(lastUpdateId);
        lastUpdateId = (newMessages.size() > 0) ? newMessages.get(newMessages.size()-1).getUpdate_id() : lastUpdateId;
        newMessages.forEach(this::notifyUpdateListeners);
        return lastUpdateId;
    }


    public void notifyUpdateListeners(Update update) {
        Message m = update.getMessage();
        for (Consumer<Message> evaluator : evaluators){
            evaluator.accept(m);
        }
    }
}