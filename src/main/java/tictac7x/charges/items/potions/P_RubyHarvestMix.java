package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_RubyHarvestMix extends _Potion {
    public P_RubyHarvestMix(Provider provider) {
        super("ruby_harvest_mix", new TriggerItem[]{
            new TriggerItem(ItemID.HUNTER_MIX_RUBY_1DOSE).fixedCharges(1),
            new TriggerItem(ItemID.HUNTER_MIX_RUBY_2DOSE).fixedCharges(2),
        }, provider);
    }
}
