package TelegramSmartHome.SmartCam;

import TelegramSmartHome.SmartCam.Config.Config;
import TelegramSmartHome.TelegramIO.MessageSendService;

public class SmartCamApplication {

	public static void main(String[] args) {
		Config config = new Config();

		MessageSendService sendService =new MessageSendService("test");
		sendService.sendMessage("foo", "bar");
	}

}
