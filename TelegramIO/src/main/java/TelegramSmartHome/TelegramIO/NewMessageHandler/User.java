package TelegramSmartHome.TelegramIO.NewMessageHandler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(value = {"is_bot", "last_name"})
public class User {
    long id;
    String first_name;
    String username;
    boolean is_bot;
    String language_code;
}