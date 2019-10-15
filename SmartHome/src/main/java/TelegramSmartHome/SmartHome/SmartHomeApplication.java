package TelegramSmartHome.SmartHome;

import TelegramSmartHome.SmartHome.Config.ConfigService;
import TelegramSmartHome.SmartHome.SmartCam.SmartCam;
import TelegramSmartHome.SmartHome.SystemService.SystemService;
import TelegramSmartHome.SmartHome.UserManagement.UserService;
import TelegramSmartHome.TelegramIO.MessageSendService;
import TelegramSmartHome.TelegramIO.UpdateService;

public class SmartHomeApplication {

	public static void main(String[] args) {
		ConfigService config = new ConfigService();

		MessageSendService sendService = new MessageSendService(config.getBotToken());
		UpdateService updateService = new UpdateService(config.getBotToken(), config.getLastMessageId());
        UserService userService = new UserService(config);

		SmartCam cam = new SmartCam(updateService,sendService);
		SystemService system = new SystemService(updateService,sendService, userService, config);

		updateService.start();
	}

}
