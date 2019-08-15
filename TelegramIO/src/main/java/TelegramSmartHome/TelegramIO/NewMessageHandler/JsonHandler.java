package TelegramSmartHome.TelegramIO.NewMessageHandler;

import TelegramSmartHome.TelegramIO.HttpsApiComm.HttpsHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

import java.util.List;
import java.util.stream.Collectors;

public class JsonHandler {

    private String token;
    private HttpsHandler httpsHandler;

    public JsonHandler(String botToken, HttpsHandler httpsHandler) {
        this.token = botToken;
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
