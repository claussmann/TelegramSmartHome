package TelegramSmartHome.SmartHome.SmartCam;

import TelegramSmartHome.TelegramIO.IMessageEvaluator;
import TelegramSmartHome.TelegramIO.NewMessageHandler.Message;
import TelegramSmartHome.TelegramIO.MessageSendService;

public class SmartCamMessageEvaluator implements IMessageEvaluator {
    public SmartCamMessageEvaluator(MessageSendService sendService) {

    }

    @Override
    public void processMessage(Message message) {
        System.out.println(message.getMessageText());
    }
}
