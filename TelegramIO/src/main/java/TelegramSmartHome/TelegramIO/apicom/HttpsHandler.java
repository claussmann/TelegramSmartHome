package TelegramSmartHome.TelegramIO.apicom;

import io.vavr.control.Try;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Logger;

public class HttpsHandler {

    private String token;
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public HttpsHandler(String botToken) {
        this.token = botToken;
    }

    public String httpsGetRequest() {
        StringBuilder responseBody = new StringBuilder();
        URL url = Try.of(() -> new URL("https://api.telegram.org/bot" + token + "/getUpdates"))
                .getOrNull();
        logger.info("URL set");
        URLConnection urlConnection = Try.of(url :: openConnection)
                .getOrNull();
        logger.info("Opened URL Connection");
        InputStream inputStream = Try.of(urlConnection :: getInputStream)
                .getOrElse(new ByteArrayInputStream( "".getBytes()));
        logger.info("Pulling InputStream");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String input = Try.of(bufferedReader :: readLine).getOrNull();
        logger.info("Successfully read input");
        while (input != null) {
            responseBody.append(input);
            input = Try.of(bufferedReader :: readLine).getOrNull();
        }
        logger.info("Build response body");
        Try.run(bufferedReader :: close).recover(Exception.class, e -> {e.printStackTrace(); logger.severe("Can't close buffer"); return null;});
        return responseBody.toString();
    }

    public void httpsPushRequest(long chatId, String message) {
        String messageTextEncoded = Try.of(() -> URLEncoder.encode(message, "UTF-8")).getOrElse("");
        logger.info("Encoded message -> UTF-8");
        String urlParameters = "chat_id=" + chatId + "&text=" + messageTextEncoded;
        logger.info("Build URL-Parameters");
        try {
            URL url = new URL("https://api.telegram.org/bot" + token + "/sendMessage");
            logger.info("Build URL");

            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            logger.info("Setup URL-Connection");

            OutputStreamWriter parameterWriter = new OutputStreamWriter(connection.getOutputStream());
            parameterWriter.write(urlParameters);
            parameterWriter.flush();
            parameterWriter.close();
            logger.info("Pushed request successfully");
            //Has to be closed or read. Process blocks otherwise.
            connection.getInputStream().close();
            logger.info("Closed connection");
        } catch (Exception e) {
            logger.severe("Failed to push Request");
            e.printStackTrace();
        }
    }
}
