package TelegramSmartHome.TelegramIO;

import TelegramSmartHome.TelegramIO.apicom.JsonHandler;
import TelegramSmartHome.TelegramIO.message.Message;
import TelegramSmartHome.TelegramIO.message.Update;
import io.vavr.control.Try;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class UpdateService {
    private long lastUpdateId;
    List<Consumer<Message>> evaluators;
    private JsonHandler jsonHandler;
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


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
        logger.info("Added evaluator");
    }

    /**
     * Starts listening for updates. Notifies all registered IMessageEvaluators.
     * Use addUpdateListener to add an Evaluator.
     */
    public void start(){
        while (true){
            getUpdates();
            logger.severe("Fetched newest Updates");
            Try.of(() -> {Thread.sleep(10000); return null;});
        }
    }

    public long getUpdates(){
        List<Update> newMessages = jsonHandler.getNewMessages(lastUpdateId);
        logger.info("Fetched messages");
        lastUpdateId = (newMessages.size() > 0) ? newMessages.get(newMessages.size()-1).getUpdate_id() : lastUpdateId;
        logger.info("Updated UpdateId");
        newMessages.forEach(this::notifyUpdateListeners);
        logger.info("notified listeners");
        return lastUpdateId;
    }


    public void notifyUpdateListeners(Update update) {
        Message m = update.getMessage();
        for (Consumer<Message> evaluator : evaluators){
            evaluator.accept(m);
        }
    }
}