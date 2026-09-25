package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.ItemID;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class EchoAhrimsStaff extends _BarrowsItem {
    public EchoAhrimsStaff(Provider provider) {
        super("Echo Ahrim's weapon", "Ahrim's weapon", ItemID.BARROWS_AHRIM_WEAPON_ORNAMENT, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_ORNAMENT).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_ORNAMENT_100),
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_ORNAMENT_75),
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_ORNAMENT_50),
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_ORNAMENT_25),
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_ORNAMENT_BROKEN).fixedCharges(0),
        };
    }
}