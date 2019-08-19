package TelegramSmartHome.TelegramIO.message;

import lombok.Data;

@Data
public class Update {
    long update_id;
    Message message;
}