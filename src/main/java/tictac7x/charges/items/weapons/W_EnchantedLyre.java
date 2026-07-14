package tictac7x.charges.items.weapons;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class W_EnchantedLyre extends ChargedItem {
    public W_EnchantedLyre(Provider provider) {
        super(TicTac7xChargesImprovedConfig.enchanted_lyre, ItemId.ENCHANTED_LYRE_0, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ENCHANTED_LYRE_0).fixedCharges(0),
            new TriggerItem(ItemId.ENCHANTED_LYRE_1).fixedCharges(1),
            new TriggerItem(ItemId.ENCHANTED_LYRE_2).fixedCharges(2),
            new TriggerItem(ItemId.ENCHANTED_LYRE_3).fixedCharges(3),
            new TriggerItem(ItemId.ENCHANTED_LYRE_4).fixedCharges(4),
            new TriggerItem(ItemId.ENCHANTED_LYRE_5).fixedCharges(5),
            new TriggerItem(ItemId.ENCHANTED_LYRE_IMBUED).unlimitedCharges(),
        };

        this.triggers.addAll(List.of(
            new OnMenuEntryAdded("Play").replaceOption("Teleport")
        ));
    }
}
