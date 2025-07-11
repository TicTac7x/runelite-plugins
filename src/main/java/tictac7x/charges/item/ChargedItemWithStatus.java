package tictac7x.charges.item;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
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

        return status.get().equals(TicTac7xChargesImprovedConfig.ItemActivity.DEACTIVATED.toString());
    }

    public boolean isActivated() {
        final Optional<String> status = Optional.ofNullable(provider.configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, getConfigStatusKey()));

        if (!status.isPresent()) {
            return false;
        }

        return status.get().equals(TicTac7xChargesImprovedConfig.ItemActivity.ACTIVATED.toString());
    }

    public String getConfigStatusKey() {
        return configKey + "_status";
    }

    public void deactivate() {
        setActivity(TicTac7xChargesImprovedConfig.ItemActivity.DEACTIVATED);
    }

    public void activate() {
        setActivity(TicTac7xChargesImprovedConfig.ItemActivity.ACTIVATED);
    }

    private void setActivity(final TicTac7xChargesImprovedConfig.ItemActivity status) {
        provider.configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, getConfigStatusKey(), status);
    }

    @Override
    public Color getTextColor(final int itemId) {
        final Color defaultColor = super.getTextColor(itemId);

        if (defaultColor == provider.config.getColorEmpty() || isDeactivated()) {
            return provider.config.getColorEmpty();
        }

        if (isActivated()) {
            return provider.config.getColorActivated();
        }

        return defaultColor;
    }

    @Override
    public Color getTotalTextColor() {
        final Color defaultColor = super.getTotalTextColor();

        if (defaultColor == provider.config.getColorEmpty() || isDeactivated()) {
            return provider.config.getColorEmpty();
        }

        if (isActivated()) {
            return provider.config.getColorActivated();
        }

        return defaultColor;
    }
}
