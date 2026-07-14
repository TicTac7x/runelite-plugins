package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_RelycimsBalm extends _Potion {
    public P_RelycimsBalm(Provider provider) {
        super("relicyms_balm", new TriggerItem[]{
            new TriggerItem(ItemID.RELICYMS_BALM1).fixedCharges(1),
            new TriggerItem(ItemID.RELICYMS_BALM2).fixedCharges(2),
            new TriggerItem(ItemID.RELICYMS_BALM3).fixedCharges(3),
            new TriggerItem(ItemID.RELICYMS_BALM4).fixedCharges(4),
        }, provider);
    }
}
