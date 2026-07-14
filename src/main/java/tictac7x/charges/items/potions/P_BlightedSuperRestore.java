package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_BlightedSuperRestore extends _Potion {
    public P_BlightedSuperRestore(Provider provider) {
        super("bligted_super_restore", new TriggerItem[]{
            new TriggerItem(ItemID.BLIGHTED_1DOSE2RESTORE).fixedCharges(1),
            new TriggerItem(ItemID.BLIGHTED_2DOSE2RESTORE).fixedCharges(2),
            new TriggerItem(ItemID.BLIGHTED_3DOSE2RESTORE).fixedCharges(3),
            new TriggerItem(ItemID.BLIGHTED_4DOSE2RESTORE).fixedCharges(4),
        }, provider);
    }
}
