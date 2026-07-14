package tictac7x.charges.items.jewelry;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.enums.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class J_AmuletOfBloodFury extends ChargedItem {
    public J_AmuletOfBloodFury(Provider provider) {
        super(TicTac7xChargesImprovedConfig.amulet_of_blood_fury, ItemId.AMULET_OF_BLOOD_FURY, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.AMULET_OF_BLOOD_FURY),
        };

        this.triggers.addAll(List.of(
            // Creation
            new OnChatMessage("You have successfully created an Amulet of blood fury.").setFixedCharges(10000),

            // Check.
            new OnChatMessage("Your Amulet of blood fury (will work for|can perform) (?<charges>.+) more hits?.").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You have successfully added .+ hits? to your Amulet of blood fury. It will now work for (?<charges>.+) more hits?.").setDynamicallyCharges(),

            // Take damage.
            new OnHitsplatApplied(HitsplatTarget.ENEMY, HitsplatGroup.SUCCESSFUL).combatStyle(CombatStyle.MELEE).isEquipped().decreaseCharges(1)
        ));
    }
}
