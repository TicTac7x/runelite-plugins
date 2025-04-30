package tictac7x.charges.items.shields;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.OnResetDaily;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class S_FaladorShield extends ChargedItem {
    public S_FaladorShield(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.falador_shield, ItemId.FALADOR_SHIELD_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.FALADOR_SHIELD_1),
            new TriggerItem(ItemId.FALADOR_SHIELD_2),
            new TriggerItem(ItemId.FALADOR_SHIELD_3),
            new TriggerItem(ItemId.FALADOR_SHIELD_4),
        };
        
        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("You have one remaining charge for today.").onItemClick().setFixedCharges(1),
            new OnChatMessage("You have two remaining charges for today.").onItemClick().setFixedCharges(2),

            // Teleport when empty.
            new OnChatMessage("You have already used (both )?your charge(s)? for today.").onItemClick().setFixedCharges(0),
            new OnChatMessage("You have already used all available recharges today. Try again tomorrow when the shield has recharged.").onItemClick().setFixedCharges(0),

            // Recharge prayer.
            new OnGraphicChanged(321).onItemClick().decreaseCharges(1),

            // Daily resets.
            new OnResetDaily().specificItem(ItemId.FALADOR_SHIELD_1).setFixedCharges(1),
            new OnResetDaily().specificItem(ItemId.FALADOR_SHIELD_2).setFixedCharges(1),
            new OnResetDaily().specificItem(ItemId.FALADOR_SHIELD_3).setFixedCharges(1),
            new OnResetDaily().specificItem(ItemId.FALADOR_SHIELD_4).setFixedCharges(2),
        };
    }
}
