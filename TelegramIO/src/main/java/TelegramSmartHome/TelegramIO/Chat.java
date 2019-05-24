package TelegramSmartHome.TelegramIO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(value = {"last_name"})
public class Chat {
    int id;
    String first_name;
    String username;
    String type;
}