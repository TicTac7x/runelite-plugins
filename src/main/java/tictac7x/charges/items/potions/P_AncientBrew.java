package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_AncientBrew extends _Potion {
    public P_AncientBrew(Provider provider) {
        super("ancient_brew", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSEANCIENTBREW).fixedCharges(1),
            new TriggerItem(ItemID._2DOSEANCIENTBREW).fixedCharges(2),
            new TriggerItem(ItemID._3DOSEANCIENTBREW).fixedCharges(3),
            new TriggerItem(ItemID._4DOSEANCIENTBREW).fixedCharges(4),
        }, provider);
    }
}
