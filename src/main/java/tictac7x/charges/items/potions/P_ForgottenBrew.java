package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_ForgottenBrew extends _Potion {
    public P_ForgottenBrew(Provider provider) {
        super("forgotten_brew", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSEFORGOTTENBREW).fixedCharges(1),
            new TriggerItem(ItemID._2DOSEFORGOTTENBREW).fixedCharges(2),
            new TriggerItem(ItemID._3DOSEFORGOTTENBREW).fixedCharges(3),
            new TriggerItem(ItemID._4DOSEFORGOTTENBREW).fixedCharges(4),
        }, provider);
    }
}
