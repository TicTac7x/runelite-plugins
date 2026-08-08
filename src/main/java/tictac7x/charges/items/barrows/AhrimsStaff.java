package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class AhrimsStaff extends _BarrowsItem {
    public AhrimsStaff(Provider provider) {
        super("Ahrim's weapon", ItemID.BARROWS_AHRIM_WEAPON, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_100),
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_75),
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_50),
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_25),
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_BROKEN).fixedCharges(0),

            // Echo ornament kit variants (Leagues V: Raging Echoes).
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_ORNAMENT).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_ORNAMENT_100),
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_ORNAMENT_75),
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_ORNAMENT_50),
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_ORNAMENT_25),
            new TriggerItem(ItemID.BARROWS_AHRIM_WEAPON_ORNAMENT_BROKEN).fixedCharges(0),
        };
    }
}