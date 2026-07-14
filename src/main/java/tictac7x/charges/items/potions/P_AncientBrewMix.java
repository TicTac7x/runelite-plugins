package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_AncientBrewMix extends _Potion {
    public P_AncientBrewMix(Provider provider) {
        super("ancient_brew_mix", new TriggerItem[]{
            new TriggerItem(ItemId.ANCIENT_BREW_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.ANCIENT_BREW_MIX_2).fixedCharges(2),
        }, provider);
    }
}
