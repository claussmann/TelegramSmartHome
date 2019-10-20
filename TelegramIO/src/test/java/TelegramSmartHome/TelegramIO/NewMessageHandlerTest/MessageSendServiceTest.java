package TelegramSmartHome.TelegramIO.NewMessageHandlerTest;

import TelegramSmartHome.TelegramIO.MessageSendService;
import TelegramSmartHome.TelegramIO.apicom.HttpsHandler;
import TelegramSmartHome.TelegramIO.message.Message;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class MessageSendServiceTest {

    static HttpsHandler httpsHandler;
    static MessageSendService messageSendService;


    @BeforeClass
    public static void before() {
        httpsHandler = mock(HttpsHandler.class);
        messageSendService = new MessageSendService(httpsHandler);
    }

    @Test
    public void sendOneMessage() {
        messageSendService.sendMessage(1, "Test");
        verify(httpsHandler, times(1)).httpsPushRequest(1, "Test");
    }
}
