package tictac7x.charges.item;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Store;

import java.util.Optional;

public class ChargedItem extends ChargedItemBase {
    public ChargedItem(String configKey, int itemId, Client client, ClientThread clientThread, ConfigManager configManager, ItemManager itemManager, InfoBoxManager infoBoxManager, ChatMessageManager chatMessageManager, Notifier notifier, TicTac7xChargesImprovedConfig config, Store store, final Gson gson) {
        super(configKey, itemId, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store);
    }

    @Override
    public String getCharges() {
        for (final TriggerItem triggerItem : items) {
            if (triggerItem.itemId == itemId && triggerItem.fixedCharges.isPresent()) {
                return getChargesMinified(triggerItem.fixedCharges.get());
            }
        }

        if (getChargesFromConfig() == Charges.UNLIMITED) {
            return "∞";
        }

        if (getChargesFromConfig() >= 0) {
            return getChargesMinified(getChargesFromConfig());
        }

        return "?";
    }

    @Override
    public String getCharges(final int itemId) {
        for (final TriggerItem triggerItem : items) {
            if (triggerItem.itemId == itemId && triggerItem.fixedCharges.isPresent()) {
                return getChargesMinified(triggerItem.fixedCharges.get());
            }
        }

        return getCharges();
    }

    @Override
    public String getTotalCharges() {
        int totalFixedCharges = 0;
        int equipmentFixedCharges = 0;
        boolean fixedItemsFound = false;

        for (final TriggerItem triggerItem : items) {
            if (triggerItem.fixedCharges.isPresent()) {
                totalFixedCharges += store.getInventoryItemQuantity(triggerItem.itemId) * triggerItem.fixedCharges.get();
                equipmentFixedCharges += store.getEquipmentItemQuantity(triggerItem.itemId) * triggerItem.fixedCharges.get();
                fixedItemsFound = true;
            }
        }

        try {
            if (getChargesFromConfig() == Charges.UNKNOWN && fixedItemsFound) {
                return equipmentFixedCharges > 0 ?
                    getChargesMinified(equipmentFixedCharges) :
                    getChargesMinified(totalFixedCharges);
            }
        } catch (final Exception ignored) {}

        return getCharges();
    }

    public void setCharges(int charges) {
        charges =
            // Unlimited
            charges == Charges.UNLIMITED ? charges :

            // 0 -> charges
            Math.max(0, charges);

        if (this.getChargesFromConfig() != charges) {
            configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, configKey, charges);
        }
    }

    public void decreaseCharges(final int charges) {
        setCharges(this.getChargesFromConfig() - charges);
    }

    public void increaseCharges(final int charges) {
        setCharges(this.getChargesFromConfig() + charges);
    }

    private int getChargesFromConfig() {
        final Optional<String> charges = Optional.ofNullable(configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, configKey));

        if (!charges.isPresent()) {
            return Charges.UNKNOWN;
        }

        try {
            return Integer.parseInt(charges.get());
        } catch (final Exception ignored) {
            return Charges.UNKNOWN;
        }
    }
}


