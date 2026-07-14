package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_SaradominBrew extends _Potion {
    public P_SaradominBrew(Provider provider) {
        super("saradomin_brew", new TriggerItem[]{
            new TriggerItem(ItemId.SARADOMIN_BREW_1).fixedCharges(1),
            new TriggerItem(ItemId.SARADOMIN_BREW_2).fixedCharges(2),
            new TriggerItem(ItemId.SARADOMIN_BREW_3).fixedCharges(3),
            new TriggerItem(ItemId.SARADOMIN_BREW_4).fixedCharges(4),
        }, provider);
    }
}
