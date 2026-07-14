package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.*;

public class P_SuperMagic extends _Potion {
    public P_SuperMagic(Provider provider) {
        super("super_magic", new TriggerItem[]{
            new TriggerItem(ItemID.NZONE1DOSE2MAGICPOTION).fixedCharges(1),
            new TriggerItem(ItemID.NZONE2DOSE2MAGICPOTION).fixedCharges(2),
            new TriggerItem(ItemID.NZONE3DOSE2MAGICPOTION).fixedCharges(3),
            new TriggerItem(ItemID.NZONE4DOSE2MAGICPOTION).fixedCharges(4),
        }, provider);
    }
}
