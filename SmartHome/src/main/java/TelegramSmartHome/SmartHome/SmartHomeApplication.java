package TelegramSmartHome.SmartHome;

import TelegramSmartHome.SmartHome.Config.Config;
import TelegramSmartHome.TelegramIO.MessageSendService;

public class SmartHomeApplication {

	public static void main(String[] args) {
		Config config = new Config();

		MessageSendService sendService =new MessageSendService(config.getBotToken());
		sendService.sendMessage("foo", "bar");
	}

}
