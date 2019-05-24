package TelegramSmartHome.TelegramIO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Result {
    boolean ok;
    @JsonProperty("result")
    List<Update> updates;
}