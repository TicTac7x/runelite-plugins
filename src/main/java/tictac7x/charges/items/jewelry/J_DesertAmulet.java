package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class J_DesertAmulet extends ChargedItem {
    public J_DesertAmulet(Provider provider) {
        super(TicTac7xChargesImprovedConfig.desert_amulet, ItemID.DESERT_AMULET_MEDIUM, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.DESERT_AMULET_MEDIUM),
            new TriggerItem(ItemID.DESERT_AMULET_HARD),
            new TriggerItem(ItemID.DESERT_AMULET_ELITE).unlimitedCharges(),
        };

        this.triggers.addAll(List.of(
            new OnChatMessage("You have already used your available teleports for today.").setFixedCharges(0),
            new OnResetDaily().specificItem(ItemID.DESERT_AMULET_MEDIUM).setFixedCharges(1),
            new OnResetDaily().specificItem(ItemID.DESERT_AMULET_HARD).setFixedCharges(1)
        ));
    }
}
