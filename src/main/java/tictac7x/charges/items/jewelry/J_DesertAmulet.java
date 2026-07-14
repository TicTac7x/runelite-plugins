package tictac7x.charges.items.jewelry;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class J_DesertAmulet extends ChargedItem {
    public J_DesertAmulet(Provider provider) {
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
