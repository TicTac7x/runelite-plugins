package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_SuperAttack extends _Potion {
    public P_SuperAttack(final Provider provider) {
        super("super_attack", new TriggerItem[]{
            new TriggerItem(ItemId.SUPER_ATTACK_1).fixedCharges(1),
            new TriggerItem(ItemId.SUPER_ATTACK_2).fixedCharges(2),
            new TriggerItem(ItemId.SUPER_ATTACK_3).fixedCharges(3),
            new TriggerItem(ItemId.SUPER_ATTACK_4).fixedCharges(4),
        }, provider);
    }
}
