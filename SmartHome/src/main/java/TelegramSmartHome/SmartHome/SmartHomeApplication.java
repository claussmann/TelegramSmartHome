package TelegramSmartHome.SmartHome;

import TelegramSmartHome.SmartHome.Config.Config;
import TelegramSmartHome.SmartHome.SmartCam.SmartCam;
import TelegramSmartHome.TelegramIO.MessageSendService;
import TelegramSmartHome.TelegramIO.UpdateService;

public class SmartHomeApplication {

	public static void main(String[] args) {
		Config config = new Config();

		MessageSendService sendService = new MessageSendService(config.getBotToken());
		UpdateService updateService = new UpdateService(config.getBotToken(), config.getLastMessageID());

		SmartCam cam = new SmartCam(updateService,sendService);

		updateService.start();
	}

}
