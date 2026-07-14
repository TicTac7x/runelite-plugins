package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class GuthansWarspear extends _BarrowsItem {
    public GuthansWarspear(Provider provider) {
        super("Guthan's weapon", ItemID.BARROWS_GUTHAN_WEAPON, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_GUTHAN_WEAPON).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_GUTHAN_WEAPON_100),
            new TriggerItem(ItemID.BARROWS_GUTHAN_WEAPON_75),
            new TriggerItem(ItemID.BARROWS_GUTHAN_WEAPON_50),
            new TriggerItem(ItemID.BARROWS_GUTHAN_WEAPON_25),
            new TriggerItem(ItemID.BARROWS_GUTHAN_WEAPON_BROKEN).fixedCharges(0),
        };
    }
}