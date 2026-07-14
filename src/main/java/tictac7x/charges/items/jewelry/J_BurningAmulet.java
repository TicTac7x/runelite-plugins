package tictac7x.charges.items.jewelry;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class J_BurningAmulet extends ChargedItem {
    public J_BurningAmulet(
        Provider provider
    ) {
        super(TicTac7xChargesImprovedConfig.burning_amulet, ItemId.BURNING_AMULET_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BURNING_AMULET_1).fixedCharges(1),
            new TriggerItem(ItemId.BURNING_AMULET_2).fixedCharges(2),
            new TriggerItem(ItemId.BURNING_AMULET_3).fixedCharges(3),
            new TriggerItem(ItemId.BURNING_AMULET_4).fixedCharges(4),
            new TriggerItem(ItemId.BURNING_AMULET_5).fixedCharges(5),
        };

        this.triggers.addAll(List.of(
            new OnMenuEntryAdded("Rub").replaceOption("Teleport")
        ));
    }
}