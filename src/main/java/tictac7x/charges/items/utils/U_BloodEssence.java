package tictac7x.charges.items.utils;

import net.runelite.api.gameval.*;
import net.runelite.api.widgets.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.enums.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.Provider;

import java.util.*;

public class U_BloodEssence extends ChargedItemWithStatus {
    public U_BloodEssence(Provider provider) {
        super(TicTac7xChargesImprovedConfig.blood_essence, ItemID.BLOOD_ESSENCE_INACTIVE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BLOOD_ESSENCE_INACTIVE),
            new TriggerItem(ItemID.BLOOD_ESSENCE_ACTIVE),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your blood essence has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Charge used.
            new OnChatMessage("You manage to extract power from the Blood Essence and craft (?<charges>.+) extra runes?.").decreaseDynamicallyCharges(),

            // Depleted.
            new OnChatMessage("Your blood essence falls apart, drained of energy.").setFixedCharges(1000).deactivate(),

            // Activate.
            new OnChatMessage("You activate the blood essence.").activate(),

            // Status from inventory.
            new OnItemContainerChanged(InventoryID.INV).itemsConsumer(items -> {
                if (items.hasItem (ItemID.BLOOD_ESSENCE_ACTIVE)) {
                    activate();
                } else if (items.hasItem (ItemID.BLOOD_ESSENCE_INACTIVE)) {
                    deactivate();
                }
            }),

            // Destroy.
            new OnScriptPreFired(1651).scriptConsumer((script) -> {
                Optional<Widget> destroyWidgetItem = TicTac7xChargesImprovedPlugin.getWidget(provider.client, 584, 5);
                if (
                    destroyWidgetItem.isPresent() &&
                    (destroyWidgetItem.get().getItemId() == ItemID.BLOOD_ESSENCE_ACTIVE || destroyWidgetItem.get().getItemId() == ItemID.BLOOD_ESSENCE_INACTIVE) &&
                    script.arguments.length >= 5 &&
                    script.arguments[4].toString().equals("Yes")
                ) {
                    provider.store.addConsumerToNextTickQueue(() -> setCharges(1000));

                    if (destroyWidgetItem.get().getItemId() == ItemID.BLOOD_ESSENCE_ACTIVE) {
                        provider.store.addConsumerToNextTickQueue(this::deactivate);
                    }
                }
            })
        ));
    }
}
