package tictac7x.charges.store;

import java.util.function.BiConsumer;

public class MyConfigManager {
    private BiConsumer<String, String> onSetConfiguration;

    public MyConfigManager(BiConsumer<String, String> onSetConfiguration) {
        this.onSetConfiguration = onSetConfiguration;
    }

    public void setConfiguration(String key, String value) {
        onSetConfiguration.accept(key, value);
    }
}
