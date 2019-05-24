package TelegramSmartHome.TelegramIO;

import lombok.Data;

@Data
public class Update {
    int update_id;
    Message message;
}