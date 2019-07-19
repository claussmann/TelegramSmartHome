package TelegramSmartHome.TelegramIO;

import TelegramSmartHome.TelegramIO.NewMessageHandler.Message;

public interface IMessageEvaluator {
    void processMessage(Message message);
}
