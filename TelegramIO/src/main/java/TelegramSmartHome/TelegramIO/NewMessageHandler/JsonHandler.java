package TelegramSmartHome.TelegramIO.NewMessageHandler;

import TelegramSmartHome.TelegramIO.HttpsApiComm.HttpsHandler;
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
        return result.updates.stream()
                .filter(update -> update.update_id > lastUpdateId)
                .collect(Collectors.toList());
    }
}
