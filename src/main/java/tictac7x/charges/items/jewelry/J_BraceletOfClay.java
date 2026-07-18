package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

import java.util.*;

public class J_BraceletOfClay extends ChargedItem {
    public J_BraceletOfClay(Provider provider) {
        super(TicTac7xChargesImprovedConfig.bracelet_of_clay, ItemID.JEWL_BRACELET_OF_CLAY, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.JEWL_BRACELET_OF_CLAY).needsToBeEquipped(),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("You can mine (?<charges>.+) more pieces? of soft clay before your bracelet crumbles to dust.").setDynamicallyCharges(),

            // Mine clay.
            new OnItemContainerChanged(InventoryID.INV).isEquipped().onMenuOption("Mine").onMenuTarget("Clay rocks").consumer(() -> {
                if (provider.store.hasChatMessage("Your bracelet of clay crumbles to dust.")) return;
                int clayBefore = provider.store.getPreviousInventoryItemQuantity(ItemID.SOFTCLAY);
                int clayAfter = provider.store.getInventoryItemQuantity(ItemID.SOFTCLAY);
                decreaseCharges(clayAfter - clayBefore);
            }),

            // Mine soft clay.
            new OnItemContainerChanged(InventoryID.INV).isEquipped().onMenuOption("Mine").onMenuTarget("Soft clay rocks").consumer(() -> {
                if (provider.store.hasChatMessage("Your bracelet of clay crumbles to dust.")) return;
                int clayBefore = provider.store.getPreviousInventoryItemQuantity(ItemID.SOFTCLAY);
                int clayAfter = provider.store.getInventoryItemQuantity(ItemID.SOFTCLAY);

                // At least 2 soft clay was mined.
                if (clayAfter - clayBefore >= 2) {
                    decreaseCharges(1);
                }
            }),

            // Crumbles.
            new OnChatMessage("Your bracelet of clay crumbles to dust.").runConsumerOnNextGameTick(() -> {
                setCharges(28);
            })
        ));
    }
}
