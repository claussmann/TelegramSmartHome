package TelegramSmartHome.TelegramIO.NewMessageHandler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(value = {"last_name"})
public class Chat {
    long id;
    String first_name;
    String username;
    String type;
}