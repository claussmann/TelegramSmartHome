package TelegramSmartHome.TelegramIO;

import com.fasterxml.jackson.databind.ObjectMapper;
import  io.vavr.control.Try;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        while (true){
            getUpdates();
            Try.of(() -> {Thread.sleep(10000); return null;});
        }
    }

    private void getUpdates(){
        String jsonResponse = httpsGetRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Update> newMessages;
        Result result = Try.of(() -> objectMapper.readValue(jsonResponse, Result.class))
                .getOrElse(new Result());
        newMessages = result.updates.stream()
                    .filter(update -> update.update_id > messageOffset)
                    .collect(Collectors.toList());
        newMessages.stream().forEach(update -> notifyUpdateListeners(update));

    }

    private void notifyUpdateListeners(Update update) {
        for (IMessageEvaluator evaluator : evaluators){
            evaluator.processMessage(update.message);
        }
    }

    private String httpsGetRequest() {
        StringBuilder responseBody = new StringBuilder();
        URL url = Try.of(() -> new URL("https://api.telegram.org/bot" + token + "/getUpdates"))
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
}