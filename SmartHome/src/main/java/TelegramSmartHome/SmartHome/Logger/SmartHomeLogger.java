package TelegramSmartHome.SmartHome.Logger;

import io.vavr.control.Try;

import java.util.logging.*;

public class SmartHomeLogger {

    static private FileHandler fileTxt;
    static private SimpleFormatter formatter;

    public static void setup(){
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        //reset global logger so only fileHandler is used for logging (no console logging)
        LogManager.getLogManager().reset();

        //Log all levels
        logger.setLevel(Level.ALL);

        //log to log.txt
        Try.of(() -> fileTxt = new FileHandler("log.txt")).getOrNull();
        formatter = new SimpleFormatter();
        fileTxt.setFormatter(formatter);
        logger.addHandler(fileTxt);

    }
}
