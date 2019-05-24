package TelegramSmartHome.TelegramIO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(value = {"is_bot", "last_name"})
public class User {
    int id;
    String first_name;
    String username;
    boolean is_bot;
    String language_code;
}