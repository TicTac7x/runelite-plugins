package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_SaradominBrew extends _Potion {
    public P_SaradominBrew(Provider provider) {
        super("saradomin_brew", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSEPOTIONOFSARADOMIN).fixedCharges(1),
            new TriggerItem(ItemID._2DOSEPOTIONOFSARADOMIN).fixedCharges(2),
            new TriggerItem(ItemID._3DOSEPOTIONOFSARADOMIN).fixedCharges(3),
            new TriggerItem(ItemID._4DOSEPOTIONOFSARADOMIN).fixedCharges(4),
        }, provider);
    }
}
