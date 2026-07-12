package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnResetDaily;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.util.List;

public class J_DesertAmulet extends ChargedItem {
    public J_DesertAmulet(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.desert_amulet, ItemId.DESERT_AMULET_2, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.DESERT_AMULET_2),
            new TriggerItem(ItemId.DESERT_AMULET_3),
            new TriggerItem(ItemId.DESERT_AMULET_4).unlimitedCharges(),
        };

        this.triggers.addAll(List.of(
            new OnChatMessage("You have already used your available teleports for today.").setFixedCharges(0),
            new OnResetDaily().specificItem(ItemId.DESERT_AMULET_2).setFixedCharges(1),
            new OnResetDaily().specificItem(ItemId.DESERT_AMULET_3).setFixedCharges(1)
        ));
    }
}
