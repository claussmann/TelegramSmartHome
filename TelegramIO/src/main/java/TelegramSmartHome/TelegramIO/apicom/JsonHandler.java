package TelegramSmartHome.TelegramIO.apicom;

import TelegramSmartHome.TelegramIO.message.Result;
import TelegramSmartHome.TelegramIO.message.Update;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class JsonHandler {

    private HttpsHandler httpsHandler;
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    public JsonHandler(HttpsHandler httpsHandler) {
        this.httpsHandler = httpsHandler;
    }



    public List<Update> getNewMessages(long lastUpdateId) {
        String jsonResponse = httpsHandler.httpsGetRequest();
        logger.info("Fetched Json Response");
        ObjectMapper objectMapper = new ObjectMapper();
        Result result = Try.of(() -> objectMapper.readValue(jsonResponse, Result.class))
                .getOrElse(new Result());
        logger.info("Mapped json to objects");
        return result.getUpdates().stream()
                .filter(update -> update.getUpdate_id() > lastUpdateId)
                .collect(Collectors.toList());
    }
}
