package TelegramSmartHome.SmartHome;

import TelegramSmartHome.SmartHome.Config.ConfigService;
import TelegramSmartHome.SmartHome.SmartCam.SmartCam;
import TelegramSmartHome.SmartHome.SystemService.SystemService;
import TelegramSmartHome.SmartHome.UserManagement.UserService;
import TelegramSmartHome.TelegramIO.MessageSendService;
import TelegramSmartHome.TelegramIO.UpdateService;
import TelegramSmartHome.TelegramIO.apicom.HttpsHandler;
import TelegramSmartHome.TelegramIO.apicom.JsonHandler;

public class SmartHomeApplication {
	static ConfigService config;
	static MessageSendService sendService;
	static UpdateService updateService;
	static UserService userService;
	static SmartCam cam;
	static SystemService system;
	static HttpsHandler httpsService;
	static JsonHandler jsonHandler;

	public static void main(String[] args) {
		config = new ConfigService();
		setExitListeners();
		run();
	}

	public static void restartApplication(){
		run();
	}

	public static void run(){
		httpsService = new HttpsHandler(config.getBotToken());
		jsonHandler = new JsonHandler(httpsService);
		sendService = new MessageSendService(httpsService);
		updateService = new UpdateService(config.getLastMessageId(),jsonHandler);
		userService = new UserService(config);
		config.setSendService(sendService);
		config.setUpdateService(updateService);
		config.setUserService(userService);
		cam = new SmartCam(updateService,sendService);
		system = new SystemService(updateService,sendService, userService);
		updateService.start();
	}

	private static void setExitListeners(){
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			config.setLastMessageId(updateService.getLastUpdateId());
		}));
	}
}
