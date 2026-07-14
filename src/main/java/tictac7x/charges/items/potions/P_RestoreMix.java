package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_RestoreMix extends _Potion {
    public P_RestoreMix(Provider provider) {
        super("restore_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSESTATRESTORE).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSESTATRESTORE).fixedCharges(2),
        }, provider);
    }
}
