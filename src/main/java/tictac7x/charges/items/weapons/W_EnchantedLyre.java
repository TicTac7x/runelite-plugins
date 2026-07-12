package tictac7x.charges.items.weapons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.util.List;

public class W_EnchantedLyre extends ChargedItem {
    public W_EnchantedLyre(final Provider provider) {
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
