package TelegramSmartHome.TelegramIO.HttpsApiComm;

import io.vavr.control.Try;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class HttpsHandler {

    private String token;

    public HttpsHandler(String botToken) {
        this.token = botToken;
    }

    public String httpsGetRequest() {
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

    public void httpsPushRequest(long chatId, String message) {
        String messageTextEncoded = Try.of(() -> URLEncoder.encode(message, "UTF-8")).getOrElse("");
        String urlParameters = "chat_id=" + chatId + "&text=" + messageTextEncoded;

        try {
            URL url = new URL("https://api.telegram.org/bot" + token + "/sendMessage");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);

            OutputStreamWriter parameterWriter = new OutputStreamWriter(connection.getOutputStream());
            parameterWriter.write(urlParameters);
            parameterWriter.flush();
            parameterWriter.close();

            //Has to be closed or read. Process blocks otherwise.
            connection.getInputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
