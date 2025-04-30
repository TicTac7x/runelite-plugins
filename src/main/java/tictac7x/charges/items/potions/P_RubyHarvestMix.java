package tictac7x.charges.items.potions;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class P_RubyHarvestMix extends _Potion {
    public P_RubyHarvestMix(final Provider provider) {
        super("ruby_harvest_mix", new TriggerItem[]{
            new TriggerItem(ItemId.RUBY_HARVEST_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.RUBY_HARVEST_MIX_2).fixedCharges(2),
        }, provider);
    }
}
