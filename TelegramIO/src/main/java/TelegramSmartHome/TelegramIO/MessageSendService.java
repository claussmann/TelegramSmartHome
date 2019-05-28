package TelegramSmartHome.TelegramIO;

import io.vavr.control.Try;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MessageSendService {

    private String token;

    public MessageSendService(String botToken) {
        this.token = botToken;
    }

    public void sendMessage(int recipientUserId, String messageText) {
        String messageTextEncoded = Try.of(() -> {
            return URLEncoder.encode(messageText, "UTF-8");
        }).getOrElse("");

        String urlParameters = "chat_id=" + recipientUserId + "&text=" + messageTextEncoded;

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
