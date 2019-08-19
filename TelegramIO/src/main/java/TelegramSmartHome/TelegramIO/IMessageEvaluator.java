package TelegramSmartHome.TelegramIO;

import TelegramSmartHome.TelegramIO.message.Message;

public interface IMessageEvaluator {
    void processMessage(Message message);
}
