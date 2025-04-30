package tictac7x.charges.items.utils;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStatus;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnItemContainerChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemContainerId;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class U_BloodEssence extends ChargedItemWithStatus {
    public U_BloodEssence(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.blood_essence, ItemId.BLOOD_ESSENCE_INACTIVE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BLOOD_ESSENCE_INACTIVE),
            new TriggerItem(ItemId.BLOOD_ESSENCE_ACTIVE),
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your blood essence has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Charge used.
            new OnChatMessage("You manage to extract power from the Blood Essence and craft (?<charges>.+) extra runes?.").decreaseDynamicallyCharges(),

            // Status from inventory.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).itemsConsumer(items -> {
                if (items.hasItem(ItemId.BLOOD_ESSENCE_INACTIVE)) {
                    deactivate();
                } else if (items.hasItem(ItemId.BLOOD_ESSENCE_ACTIVE)) {
                    activate();
                }
            }),
        };
    }
}
