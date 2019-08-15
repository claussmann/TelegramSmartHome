package TelegramSmartHome.TelegramIO.NewMessageHandlerTest;

import TelegramSmartHome.TelegramIO.HttpsApiComm.HttpsHandler;
import TelegramSmartHome.TelegramIO.NewMessageHandler.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonHandlerTest {

    static String testJson;
    static String testSecondJson;
    static JsonHandler jsonHandler;
    static HttpsHandler httpsHandler;
    static List<Update> newMessages;
    static Message expectedMessage;

    @BeforeClass
    public static void before() {
        testJson = "{\"ok\":true,\"result\":[{\"update_id\":1,\"message\":{\"message_id\":2,\"from\":{\"id\":42,\"is_bot\":false,\"first_name\":\"Test\",\"last_name\":\"Test\",\"username\":\"TestUser\",\"language_code\":\"en\"},\"chat\":{\"id\":42,\"first_name\":\"Test\",\"last_name\":\"Test\",\"username\":\"TestUser\",\"type\":\"private\"},\"date\":1563531123,\"text\":\"Hallp\"}}]}";
        testSecondJson = "{\"ok\":true,\"result\":[{\"update_id\":2,\"message\":{\"message_id\":3,\"from\":{\"id\":42,\"is_bot\":false,\"first_name\":\"Test\",\"last_name\":\"Test\",\"username\":\"TestUser2\",\"language_code\":\"en\"},\"chat\":{\"id\":42,\"first_name\":\"Test\",\"last_name\":\"Test\",\"username\":\"TestUser2\",\"type\":\"private\"},\"date\":1563531123,\"text\":\"User2\"}}]}";
        httpsHandler = mock(HttpsHandler.class);
        when(httpsHandler.httpsGetRequest()).thenReturn(testJson).thenReturn(testSecondJson);
        jsonHandler = new JsonHandler("token", httpsHandler);

        newMessages = jsonHandler.getNewMessages(0);

        expectedMessage = new Message();
        expectedMessage.setMessage_id(2);
        expectedMessage.setDate(1563531123);
        User testUser = new User();
        testUser.set_bot(false);
        testUser.setFirst_name("Test");
        testUser.setId(42);
        testUser.setLanguage_code("en");
        testUser.setUsername("TestUser");
        expectedMessage.setFrom(testUser);
        Chat testChat = new Chat();
        testChat.setFirst_name("Test");
        testChat.setId(42);
        testChat.setType("private");
        testChat.setUsername("TestUser");
        expectedMessage.setChat(testChat);

    }


    @Test
    public void correctNumberOfMessages() {
        assertEquals(1, newMessages.size());
    }

    @Test
    public void correctUpdateId() {
        assertEquals(1, newMessages.get(0).getUpdate_id());
    }

    @Test
    public void correctMessage() {
        expectedMessage.setText("Hallp");
        assertEquals(expectedMessage, newMessages.get(0).getMessage());
    }

    @Test
    public void multipleMessages() {
        newMessages.addAll(jsonHandler.getNewMessages(1));
        assertEquals(2, newMessages.size());
        assertEquals(2, newMessages.get(1).getUpdate_id());
        assertEquals(1, newMessages.get(0).getUpdate_id());
    }

}
