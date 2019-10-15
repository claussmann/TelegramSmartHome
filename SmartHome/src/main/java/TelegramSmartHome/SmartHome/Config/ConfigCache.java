package TelegramSmartHome.SmartHome.Config;

public interface ConfigCache {
    //Called from ConfigService if cache is outdated
    void update();
}
