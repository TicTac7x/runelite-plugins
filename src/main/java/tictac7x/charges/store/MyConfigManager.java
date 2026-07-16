package tictac7x.charges.store;

import java.util.function.*;

public class MyConfigManager {
    private BiConsumer<String, Object> setConfigurationString;
    private Function<String, String> getConfiguration;

    public MyConfigManager(BiConsumer<String, Object> setConfiguration, Function<String, String> getConfiguration) {
        this.setConfigurationString = setConfiguration;
        this.getConfiguration = getConfiguration;
    }

    public void setConfiguration(String key, Object value) {
        setConfigurationString.accept(key, value);
    }

    public String getConfiguration(String key) {
        return getConfiguration.apply(key);
    }
}
