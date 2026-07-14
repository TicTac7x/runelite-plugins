package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.*;

public class P_SuperRestoreMix extends _Potion {
    public P_SuperRestoreMix(Provider provider) {
        super("super_restore_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE2RESTORE).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE2RESTORE).fixedCharges(2),
        }, provider);
    }
}
