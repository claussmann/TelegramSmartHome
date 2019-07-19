package TelegramSmartHome.TelegramIO.NewMessageHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;

public class JsonHandler {

    private String token;

    public JsonHandler(String botToken) {
        this.token = botToken;
    }

    private String httpsGetRequest() {
        StringBuilder responseBody = new StringBuilder();
        URL url = Try.of(() -> new URL("https://api.telegram.org/bot" + token + "/getNewMessages"))
                .getOrNull();
        URLConnection urlConnection = Try.of(url :: openConnection)
                .getOrNull();
        InputStream inputStream = Try.of(urlConnection :: getInputStream)
                .getOrElse(new ByteArrayInputStream( "".getBytes()));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String input = Try.of(bufferedReader :: readLine).getOrNull();
        while (input != null) {
            responseBody.append(input);
            input = Try.of(bufferedReader :: readLine).getOrNull();
        }
        Try.run(bufferedReader :: close).recover(Exception.class, e -> {e.printStackTrace(); return null;});
        return responseBody.toString();
    }

    public List<Update> getNewMessages(int lastUpdateId) {
        String jsonResponse = httpsGetRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Update> newMessages;
        Result result = Try.of(() -> objectMapper.readValue(jsonResponse, Result.class))
                .getOrElse(new Result());
        return result.updates.stream()
                .filter(update -> update.update_id > lastUpdateId)
                .collect(Collectors.toList());
    }
}
