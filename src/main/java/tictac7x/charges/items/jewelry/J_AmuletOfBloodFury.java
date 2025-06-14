package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.*;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.enums.CombatStyle;
import tictac7x.charges.store.enums.HitsplatGroup;
import tictac7x.charges.store.enums.HitsplatTarget;
import tictac7x.charges.store.ids.ItemId;

public class J_AmuletOfBloodFury extends ChargedItem {
    public J_AmuletOfBloodFury(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.amulet_of_blood_fury, ItemId.AMULET_OF_BLOOD_FURY, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.AMULET_OF_BLOOD_FURY),
        };

        this.triggers = new TriggerBase[]{
            // Creation
            new OnChatMessage("You have successfully created an Amulet of blood fury.").setFixedCharges(10000),

            // Check.
            new OnChatMessage("Your Amulet of blood fury (will work for|can perform) (?<charges>.+) more hits?.").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You have successfully added .+ hits? to your Amulet of blood fury. It will now work for (?<charges>.+) more hits?.").setDynamicallyCharges(),

            // Take damage.
            new OnHitsplatApplied(HitsplatTarget.ENEMY, HitsplatGroup.SUCCESSFUL).combatStyle(CombatStyle.MELEE).isEquipped().decreaseCharges(1),
        };
    }
}
