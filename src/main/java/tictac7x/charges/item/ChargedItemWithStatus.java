package tictac7x.charges.item;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.store.ItemActivity;
import tictac7x.charges.store.Provider;

import java.awt.Color;
import java.util.Optional;

public class ChargedItemWithStatus extends ChargedItem {

    public ChargedItemWithStatus(String configKey, int itemId, final Provider provider) {
        super(configKey, itemId, provider);
    }

    public boolean isDeactivated() {
        final Optional<String> status = Optional.ofNullable(provider.configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, getConfigStatusKey()));

        if (!status.isPresent()) {
            return false;
        }

        return status.get().equals(ItemActivity.DEACTIVATED.toString());
    }

    public boolean isActivated() {
        final Optional<String> status = Optional.ofNullable(provider.configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, getConfigStatusKey()));

        if (!status.isPresent()) {
            return false;
        }

        return status.get().equals(ItemActivity.ACTIVATED.toString());
    }

    public String getConfigStatusKey() {
        return configKey + "_status";
    }

    public void deactivate() {
        setActivity(ItemActivity.DEACTIVATED);
    }

    public void activate() {
        setActivity(ItemActivity.ACTIVATED);
    }

    private void setActivity(final ItemActivity status) {
        provider.configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, getConfigStatusKey(), status);
    }

    @Override
    public Color getTextColor() {
        if (isActivated()) {
            return provider.config.getColorActivated();
        }

        if (isDeactivated()) {
            return provider.config.getColorEmpty();
        }

        return super.getTextColor();
    }
}
