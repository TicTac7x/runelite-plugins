package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ToragsHammers extends _BarrowsItem {
    public ToragsHammers(Provider provider) {
        super("Torag's weapon", ItemID.BARROWS_TORAG_WEAPON, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_TORAG_WEAPON).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_TORAG_WEAPON_100),
            new TriggerItem(ItemID.BARROWS_TORAG_WEAPON_75),
            new TriggerItem(ItemID.BARROWS_TORAG_WEAPON_50),
            new TriggerItem(ItemID.BARROWS_TORAG_WEAPON_25),
            new TriggerItem(ItemID.BARROWS_TORAG_WEAPON_BROKEN).fixedCharges(0)
        };
    }
}