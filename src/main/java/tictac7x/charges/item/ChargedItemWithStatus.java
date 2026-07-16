package tictac7x.charges.item;

import tictac7x.charges.*;
import tictac7x.charges.store.*;

import java.awt.*;
import java.util.*;

public class ChargedItemWithStatus extends ChargedItem {
    public ChargedItemWithStatus(String configKey, int itemId, Provider provider) {
        super(configKey, itemId, provider);
    }

    public boolean isDeactivated() {
        Optional<String> status = Optional.ofNullable(provider.configManager.getConfiguration(getConfigStatusKey()));

        if (!status.isPresent()) {
            return false;
        }

        return status.get().equals(TicTac7xChargesImprovedConfig.ItemActivity.DEACTIVATED.toString());
    }

    public boolean isActivated() {
        Optional<String> status = Optional.ofNullable(provider.configManager.getConfiguration(getConfigStatusKey()));

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

    private void setActivity(TicTac7xChargesImprovedConfig.ItemActivity status) {
        provider.configManager.setConfiguration(getConfigStatusKey(), status);
    }

    @Override
    public boolean inInventoryOrEquipment() {
        return super.inInventoryOrEquipment();
    }

    @Override
    public Color getTextColor(int itemId) {
        Color defaultColor = super.getTextColor(itemId);

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
        Color defaultColor = super.getTotalTextColor();

        if (defaultColor == provider.config.getColorEmpty() || isDeactivated()) {
            return provider.config.getColorEmpty();
        }

        if (isActivated()) {
            return provider.config.getColorActivated();
        }

        return defaultColor;
    }
}
