package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_RubyHarvestMix extends _Potion {
    public P_RubyHarvestMix(Provider provider) {
        super("ruby_harvest_mix", new TriggerItem[]{
            new TriggerItem(ItemId.RUBY_HARVEST_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.RUBY_HARVEST_MIX_2).fixedCharges(2),
        }, provider);
    }
}
