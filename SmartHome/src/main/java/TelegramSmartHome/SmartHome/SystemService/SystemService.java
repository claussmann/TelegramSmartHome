package TelegramSmartHome.SmartHome.SystemService;

import TelegramSmartHome.TelegramIO.IMessageEvaluator;
import TelegramSmartHome.TelegramIO.NewMessageHandler.Message;
import TelegramSmartHome.TelegramIO.MessageSendService;
import TelegramSmartHome.TelegramIO.UpdateService;
import io.vavr.control.Try;

public class SystemService implements IMessageEvaluator {

    private final UpdateService updateService;
    private final MessageSendService sendService;

    public SystemService(UpdateService updateService, MessageSendService sendService) {

        this.updateService = updateService;
        this.sendService = sendService;

        updateService.addUpdateListener(this);
    }

    @Override
    public void processMessage(Message message) {
        switch (message.getMessageText()){
            case "/poweroff":
                sendService.sendMessage(message.getSenderId(), "Power down!\nSee you later!");
                poweroff();
                break;
            case "/reboot":
                sendService.sendMessage(message.getSenderId(), "Rebooting now!\nGive me just a second!");
                reboot();
                break;
            case "/status":
                sendService.sendMessage(message.getSenderId(), "Waiting for commands!");
        }
    }

    private void reboot() {
        ProcessBuilder pb = new ProcessBuilder("sudo reboot");
        Try.of(pb :: start);
    }

    private void poweroff() {
        ProcessBuilder pb = new ProcessBuilder("sudo poweroff");
        Try.of(pb :: start);
    }
}
