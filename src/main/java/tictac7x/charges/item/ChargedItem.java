package tictac7x.charges.item;

import tictac7x.charges.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.Optional;

public class ChargedItem extends ChargedItemBase {
    public ChargedItem(String configKey, int itemId, Provider provider) {
        super(configKey, itemId, provider);
    }

    @Override
    public boolean inInventoryOrEquipment() {
        return super.inInventoryOrEquipment();
    }

    @Override
    public int getCharges(int itemId) {
        for (TriggerItem triggerItem : items) {
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

        for (TriggerItem triggerItem : items) {
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
        } catch (Exception ignored) {}

        return getCharges(itemId);
    }

    public void setCharges(int charges) {
        // Minimum of 0 charges.
        if (charges != ChargeId.UNKNOWN && charges != ChargeId.UNLIMITED) {
            charges = Math.max(0, charges);
        }

        // Maximum charges check.
        if (getMaxCharges().isPresent()) {
            charges = Math.min(charges, getMaxCharges().get());
        }

        if (this.getChargesFromConfig() != charges) {
            provider.configManager.setConfiguration(configKey, charges);
        }
    }

    public void decreaseCharges(int charges) {
        setCharges(this.getChargesFromConfig() - charges);
    }

    public void increaseCharges(int charges) {
        setCharges(this.getChargesFromConfig() + charges);
    }

    protected int getChargesFromConfig() {
        Optional<String> charges = Optional.ofNullable(provider.configManager.getConfiguration(configKey));

        if (!charges.isPresent()) {
            return ChargeId.UNKNOWN;
        }

        try {
            return Integer.parseInt(charges.get());
        } catch (Exception ignored) {
            return ChargeId.UNKNOWN;
        }
    }
}


