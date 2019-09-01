package TelegramSmartHome.SmartHome.SystemService;

import TelegramSmartHome.SmartHome.UserManagement.UserService;
import TelegramSmartHome.TelegramIO.IMessageEvaluator;
import TelegramSmartHome.TelegramIO.message.Message;
import TelegramSmartHome.TelegramIO.MessageSendService;
import TelegramSmartHome.TelegramIO.UpdateService;
import io.vavr.control.Try;

public class SystemService implements IMessageEvaluator {

    private final UpdateService updateService;
    private final MessageSendService sendService;
    private final UserService userService;

    public SystemService(UpdateService updateService, MessageSendService sendService, UserService userService) {
        this.userService = userService;
        this.updateService = updateService;
        this.sendService = sendService;

        updateService.addUpdateListener(this);
    }

    @Override
    public void processMessage(Message message) {
        switch (message.getMessageText()){
            case "/poweroff":
                if(userService.memberOf(message.getSenderUsername(), "administrators")) {
                    sendService.sendMessage(message.getSenderId(), "Power down!\nSee you later!");
                    poweroff();
                }
                else sendService.sendMessage(message.getSenderId(), "You are no admin.");
                break;
            case "/reboot":
                if(userService.memberOf(message.getSenderUsername(), "administrators")) {
                    sendService.sendMessage(message.getSenderId(), "Rebooting now!\nGive me just a second!");
                    reboot();
                }
                else sendService.sendMessage(message.getSenderId(), "You are no admin.");
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
