package TelegramSmartHome.SmartHome;

import TelegramSmartHome.SmartHome.Config.ConfigService;
import TelegramSmartHome.SmartHome.SmartCam.SmartCam;
import TelegramSmartHome.SmartHome.SystemService.SystemService;
import TelegramSmartHome.SmartHome.UserManagement.UserService;
import TelegramSmartHome.TelegramIO.MessageSendService;
import TelegramSmartHome.TelegramIO.UpdateService;

public class SmartHomeApplication {
	static ConfigService config;
	static MessageSendService sendService;
	static UpdateService updateService;
	static UserService userService;
	static SmartCam cam;
	static SystemService system;

	public static void main(String[] args) {
		setExitListeners();
		run();
	}

	public static void restartApplication(){
		long id = updateService.getLastUpdateId();
		config.setLastMessageId(updateService.getLastUpdateId());
		run();
	}

	public static void run(){
		config = new ConfigService();
		sendService = new MessageSendService(config.getBotToken());
		updateService = new UpdateService(config.getBotToken(), config.getLastMessageId());
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
