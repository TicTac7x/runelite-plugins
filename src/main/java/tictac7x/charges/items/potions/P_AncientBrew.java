package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_AncientBrew extends _Potion {
    public P_AncientBrew(final Provider provider) {
        super("ancient_brew", new TriggerItem[]{
            new TriggerItem(ItemId.ANCIENT_BREW_1).fixedCharges(1),
            new TriggerItem(ItemId.ANCIENT_BREW_2).fixedCharges(2),
            new TriggerItem(ItemId.ANCIENT_BREW_3).fixedCharges(3),
            new TriggerItem(ItemId.ANCIENT_BREW_4).fixedCharges(4),
        }, provider);
    }
}
