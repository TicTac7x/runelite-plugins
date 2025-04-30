package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_RelycimsBalm extends _Potion {
    public P_RelycimsBalm(final Provider provider) {
        super("relicyms_balm", new TriggerItem[]{
            new TriggerItem(ItemId.RELICYMS_BALM_1).fixedCharges(1),
            new TriggerItem(ItemId.RELICYMS_BALM_2).fixedCharges(2),
            new TriggerItem(ItemId.RELICYMS_BALM_3).fixedCharges(3),
            new TriggerItem(ItemId.RELICYMS_BALM_4).fixedCharges(4),
        }, provider);
    }
}
