package TelegramSmartHome.TelegramIO.NewMessageHandlerTest;

import TelegramSmartHome.TelegramIO.UpdateService;
import TelegramSmartHome.TelegramIO.apicom.JsonHandler;
import TelegramSmartHome.TelegramIO.message.Message;
import TelegramSmartHome.TelegramIO.message.Update;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateServiceTest {

    static UpdateService updateService;
    static JsonHandler jsonHandler;
    static Consumer<Message> evaluator;
    static Update update1;

    @BeforeClass
    public static void before() {
        jsonHandler = mock(JsonHandler.class);

        List<Update> updates = new ArrayList<>();
        update1 = new Update();
        update1.setUpdate_id(0);
        Update update2 = new Update();
        update2.setUpdate_id(1);
        Update update3 = new Update();
        update3.setUpdate_id(2);
        updates.addAll(Arrays.asList(update1, update2, update3));
        when(jsonHandler.getNewMessages(0)).thenReturn(updates);
        when(jsonHandler.getNewMessages(1)).thenReturn(updates.stream().filter(u -> u.getUpdate_id() > 1).collect(Collectors.toList()));

        evaluator = mock(Consumer.class);
    }

    @Test
    public void getUpdateId() {
        updateService = new UpdateService(0, jsonHandler);
        Assert.assertEquals(0, updateService.getLastUpdateId());
    }

    @Test
    public void getAllUpdates() {
        updateService = new UpdateService(0, jsonHandler);
        long lastUpdateId = updateService.getUpdates();
        Assert.assertEquals(2, lastUpdateId);
    }

    @Test
    public void getLastUpdate() {
        updateService = new UpdateService(1, jsonHandler);
        long lastUpdateId = updateService.getUpdates();
        Assert.assertEquals(2, lastUpdateId);
    }

    @Test
    public void notifyEvaluators() {
        updateService = new UpdateService(0, jsonHandler);
        updateService.addUpdateListener(evaluator);
        updateService.notifyUpdateListeners(update1);
        verify(evaluator, times(1)).accept(any());
    }
}
