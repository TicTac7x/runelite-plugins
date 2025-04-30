package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnResetDaily;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Provider;

public class J_DesertAmulet extends ChargedItem {
    public J_DesertAmulet(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.desert_amulet, ItemId.DESERT_AMULET_2, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.DESERT_AMULET_2),
            new TriggerItem(ItemId.DESERT_AMULET_3),
            new TriggerItem(ItemId.DESERT_AMULET_4).fixedCharges(Charges.UNLIMITED),
        };

        this.triggers = new TriggerBase[]{
            new OnChatMessage("You have already used your available teleports for today.").setFixedCharges(0),
            new OnResetDaily().specificItem(ItemId.DESERT_AMULET_2).setFixedCharges(1),
            new OnResetDaily().specificItem(ItemId.DESERT_AMULET_3).setFixedCharges(1),
        };
    }
}
