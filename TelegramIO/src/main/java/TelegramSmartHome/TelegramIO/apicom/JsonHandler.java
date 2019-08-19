package TelegramSmartHome.TelegramIO.apicom;

import TelegramSmartHome.TelegramIO.message.Result;
import TelegramSmartHome.TelegramIO.message.Update;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

import java.util.List;
import java.util.stream.Collectors;

public class JsonHandler {

    private HttpsHandler httpsHandler;

    public JsonHandler(HttpsHandler httpsHandler) {
        this.httpsHandler = httpsHandler;
    }



    public List<Update> getNewMessages(long lastUpdateId) {
        String jsonResponse = httpsHandler.httpsGetRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        Result result = Try.of(() -> objectMapper.readValue(jsonResponse, Result.class))
                .getOrElse(new Result());
        return result.getUpdates().stream()
                .filter(update -> update.getUpdate_id() > lastUpdateId)
                .collect(Collectors.toList());
    }
}
