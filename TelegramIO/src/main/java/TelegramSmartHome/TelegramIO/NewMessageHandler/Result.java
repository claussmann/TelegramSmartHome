package TelegramSmartHome.TelegramIO.NewMessageHandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Result {
    boolean ok;
    @JsonProperty("result")
    List<Update> updates = new ArrayList<>();
}