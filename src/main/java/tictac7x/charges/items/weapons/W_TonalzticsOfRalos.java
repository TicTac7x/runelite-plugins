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
            // Charge: "You apply 5 charges to your Tonalztics of Ralos."
            new OnChatMessage(".*You apply (?<charges>.+) charges to your Tonalztics of Ralos\\..*").matcherConsumer(m -> storage.clearAndPut(
                ItemID.SUNFIRESPLINTER,
                TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges"))
            )),

            // Uncharge: "You uncharge your Tonalztics of Ralos, regaining 5 sunfire splinters in the process."
            new OnChatMessage(".*You uncharge your Tonalztics of Ralos, regaining .*sunfire splinter.*").consumer(() -> storage.clear()),

            // Check: "Your Tonalztics of Ralos has 5 charges remaining."
            // TODO: The wording of the check message is not documented, verify it in game and adjust the pattern if it differs.
            new OnChatMessage(".*Your Tonalztics of Ralos has (?<charges>.+) charges? remaining\\..*").matcherConsumer(m -> storage.clearAndPut(
                ItemID.SUNFIRESPLINTER,
                TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges"))
            )),

            // Attack: one charge is used per throw, no matter if the special attack is used or not.
            // TODO: The animations are taken from the animation id list, verify both of them in game
            // (enable "Debug IDs" in the debug section of the plugin settings to see the animation ids).
            new OnAnimationChanged(
                AnimationID.HUMAN_GLAIVE_RALOS01_CHARGED_THROW,
                AnimationID.HUMAN_GLAIVE_RALOS01_CHARGED_SPECIAL
            ).isEquipped().consumer(() -> storage.remove(ItemID.SUNFIRESPLINTER, 1))
        ));
    }
}
