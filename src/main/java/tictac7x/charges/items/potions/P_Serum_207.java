package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Serum_207 extends _Potion {
    public P_Serum_207(Provider provider) {
        super("serum_207", new TriggerItem[]{
            new TriggerItem(ItemID.MORT_SERUM1).fixedCharges(1),
            new TriggerItem(ItemID.MORT_SERUM2).fixedCharges(2),
            new TriggerItem(ItemID.MORT_SERUM3).fixedCharges(3),
            new TriggerItem(ItemID.MORT_SERUM4).fixedCharges(4),
        }, provider);
    }
}
