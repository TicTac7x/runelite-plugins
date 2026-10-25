package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_TonalzticsOfRalos extends ChargedItemWithStorage {
    public W_TonalzticsOfRalos(Provider provider) {
        super(TicTac7xChargesImprovedConfig.tonalztics_of_ralos, ItemID.TONALZTICS_OF_RALOS_CHARGED, provider);

        // Both item ids are needed, otherwise the plugin does not see the item at all.
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.TONALZTICS_OF_RALOS_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.TONALZTICS_OF_RALOS_CHARGED),
        };

        // Charges are sunfire splinters, up to 20,000 of them.
        this.storage.storableItems(
            new StorableItem(ItemID.SUNFIRESPLINTER)
        ).setMaximumTotalQuantity(20_000).emptyIsNegative();

        this.triggers.addAll(List.of(
            // Add charges to an already charged weapon: "You apply an additional 50 charges to your Tonalztics of Ralos. It now has 100 charges in total."
            // The message holds the total amount of charges, not the amount that was added.
            new OnChatMessage("You apply an additional .+ charges? to your Tonalztics of Ralos. It now has (?<charges>.+) charges? in total.").matcherConsumer(m -> {
                storage.clearAndPut(
                        ItemID.SUNFIRESPLINTER,
                        TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges"))
                );
            }),

            // Charge: "You apply 5 charges to your Tonalztics of Ralos."
            new OnChatMessage("You apply (?<charges>.+) charges? to your Tonalztics of Ralos.").matcherConsumer(m -> storage.clearAndPut(
                ItemID.SUNFIRESPLINTER,
                TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges"))
            )),

            // Bank charge: "The banker charges your Tonalztics of Ralos using 14x Sunfire splinters."
            // The message holds the amount of charges that was added, so it is added to the current amount of charges.
            new OnAutoChargeMessage("Tonalztics of Ralos", "Sunfire splinters", 1, this, ItemID.SUNFIRESPLINTER),

            // Uncharge: "You uncharge your Tonalztics of Ralos, regaining 5 sunfire splinters in the process."
            new OnChatMessage("You uncharge your Tonalztics of Ralos, regaining .* sunfire splinters in the process.").consumer(() -> storage.clear()),

            // Check: "Your Tonalztics of Ralos has 5 charges remaining."
            new OnChatMessage("Your Tonalztics of Ralos has (?<charges>.+) charges? remaining.").matcherConsumer(m -> storage.clearAndPut(
                ItemID.SUNFIRESPLINTER,
                TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges"))
            )),

            // Attack: one charge is used per throw, no matter if the special attack is used or not.
            // The charged throw (10923) and the charged special attack (10914) are confirmed in game.
            // The uncharged throw (10922) and the uncharged special attack (10916) are not listed, they do not use any charges.
            new OnAnimationChanged(
                AnimationID.HUMAN_GLAIVE_RALOS01_CHARGED_THROW,
                AnimationID.HUMAN_GLAIVE_RALOS01_CHARGED_SPECIAL
            ).isEquipped().consumer(() -> storage.remove(ItemID.SUNFIRESPLINTER, 1))
        ));
    }
}
