package tictac7x.charges.items.shields;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class S_DragonfireShield extends ChargedItem {
    public S_DragonfireShield(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.dragonfire_shield, ItemId.DRAGONFIRE_SHIELD, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.DRAGONFIRE_SHIELD_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.DRAGONFIRE_SHIELD),
            new TriggerItem(ItemId.DRAGONFIRE_WARD_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.DRAGONFIRE_WARD)
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("The shield has (?<charges>.+) charges?.").setDynamicallyCharges().onItemClick(),

            // Uncharge.
            new OnChatMessage("You vent the shield's remaining charges harmlessly into the air.").setFixedCharges(0),

            // Charge collected.
            new OnChatMessage("Your dragonfire (shield|ward) glows more brightly.").increaseCharges(1),

            // Already full.
            new OnChatMessage("Your dragonfire shield is already fully charged.").setFixedCharges(50),

            // Attack.
            new OnGraphicChanged(1165).isEquipped().decreaseCharges(1),
        };
    }
}
