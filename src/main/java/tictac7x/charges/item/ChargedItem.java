package tictac7x.charges.item;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ChargeId;
import tictac7x.charges.store.Provider;

import java.util.Optional;

public class ChargedItem extends ChargedItemBase {
    public ChargedItem(final String configKey, final int itemId, final Provider provider) {
        super(configKey, itemId, provider);
    }

    @Override
    public int getCharges(final int itemId) {
        for (final TriggerItem triggerItem : items) {
            if (triggerItem.itemId == itemId && triggerItem.fixedCharges.isPresent()) {
                return triggerItem.fixedCharges.get();
            }
        }

        return getChargesFromConfig();
    }

    @Override
    public int getTotalCharges() {
        int totalFixedCharges = 0;
        int equipmentFixedCharges = 0;
        boolean fixedItemsFound = false;

        for (final TriggerItem triggerItem : items) {
            if (triggerItem.fixedCharges.isPresent()) {
                totalFixedCharges += provider.store.getInventoryItemQuantity(triggerItem.itemId) * triggerItem.fixedCharges.get();
                equipmentFixedCharges += provider.store.getEquipmentItemQuantity(triggerItem.itemId) * triggerItem.fixedCharges.get();
                fixedItemsFound = true;
            }
        }

        try {
            if (getChargesFromConfig() == ChargeId.UNKNOWN && fixedItemsFound) {
                return equipmentFixedCharges > 0 ?
                    equipmentFixedCharges :
                    totalFixedCharges;
            }
        } catch (final Exception ignored) {}

        return getCharges(itemId);
    }

    public void setCharges(int charges) {
        charges =
            // Unlimited
            charges == ChargeId.UNLIMITED ? charges :

            // 0 -> charges
            Math.max(0, charges);

        if (this.getChargesFromConfig() != charges) {
            provider.configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, configKey, charges);
        }
    }

    public void decreaseCharges(final int charges) {
        setCharges(this.getChargesFromConfig() - charges);
    }

    public void increaseCharges(final int charges) {
        setCharges(this.getChargesFromConfig() + charges);
    }

    protected int getChargesFromConfig() {
        final Optional<String> charges = Optional.ofNullable(provider.configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, configKey));

        if (!charges.isPresent()) {
            return ChargeId.UNKNOWN;
        }

        try {
            return Integer.parseInt(charges.get());
        } catch (final Exception ignored) {
            return ChargeId.UNKNOWN;
        }
    }
}


