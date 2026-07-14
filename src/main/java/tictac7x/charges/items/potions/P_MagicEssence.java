package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_MagicEssence extends _Potion {
    public P_MagicEssence(Provider provider) {
        super("magic_essence", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSEMAGICESS).fixedCharges(1),
            new TriggerItem(ItemID._2DOSEMAGICESS).fixedCharges(2),
            new TriggerItem(ItemID._3DOSEMAGICESS).fixedCharges(3),
            new TriggerItem(ItemID._4DOSEMAGICESS).fixedCharges(4),
        }, provider);
    }
}
