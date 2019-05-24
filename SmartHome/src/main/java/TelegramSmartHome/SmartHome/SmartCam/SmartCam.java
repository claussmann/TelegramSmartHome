package TelegramSmartHome.SmartHome.SmartCam;

import TelegramSmartHome.TelegramIO.MessageSendService;
import TelegramSmartHome.TelegramIO.UpdateService;

public class SmartCam {

    private final UpdateService updateService;
    private final MessageSendService sendService;

    public SmartCam(UpdateService updateService, MessageSendService sendService){

        this.updateService = updateService;
        this.sendService = sendService;

        updateService.addUpdateListener(new SmartCamMessageEvaluator( sendService));
    }

}
