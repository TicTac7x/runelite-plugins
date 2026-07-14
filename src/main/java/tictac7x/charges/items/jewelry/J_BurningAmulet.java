package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

import java.util.*;

public class J_BurningAmulet extends ChargedItem {
    public J_BurningAmulet(
        Provider provider
    ) {
        super(TicTac7xChargesImprovedConfig.burning_amulet, ItemID.BURNING_AMULET_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BURNING_AMULET_1).fixedCharges(1),
            new TriggerItem(ItemID.BURNING_AMULET_2).fixedCharges(2),
            new TriggerItem(ItemID.BURNING_AMULET_3).fixedCharges(3),
            new TriggerItem(ItemID.BURNING_AMULET_4).fixedCharges(4),
            new TriggerItem(ItemID.BURNING_AMULET_5).fixedCharges(5),
        };

        this.triggers.addAll(List.of(
            new OnMenuEntryAdded("Rub").replaceOption("Teleport")
        ));
    }
}