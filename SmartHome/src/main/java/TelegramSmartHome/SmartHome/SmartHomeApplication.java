package TelegramSmartHome.SmartHome;

import TelegramSmartHome.SmartHome.Config.Config;
import TelegramSmartHome.SmartHome.Config.ConfigService;
import TelegramSmartHome.SmartHome.SmartCam.SmartCam;
import TelegramSmartHome.SmartHome.SystemService.SystemService;
import TelegramSmartHome.TelegramIO.apicom.HttpsHandler;
import TelegramSmartHome.TelegramIO.MessageSendService;
import TelegramSmartHome.TelegramIO.UpdateService;

public class SmartHomeApplication {

	public static void main(String[] args) {
		ConfigService config = new ConfigService();

		MessageSendService sendService = new MessageSendService(new HttpsHandler(config.getBotToken()));
		UpdateService updateService = new UpdateService(config.getBotToken(), config.getLastMessageId());

		SmartCam cam = new SmartCam(updateService,sendService);
		SystemService system = new SystemService(updateService,sendService);

		updateService.start();
	}

}
