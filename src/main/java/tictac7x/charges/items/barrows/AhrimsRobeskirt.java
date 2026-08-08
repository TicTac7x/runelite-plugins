package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class AhrimsRobeskirt extends _BarrowsItem {
    public AhrimsRobeskirt(Provider provider) {
        super("Ahrim's skirt", ItemID.BARROWS_AHRIM_LEGS, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_AHRIM_LEGS).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_AHRIM_LEGS_100),
            new TriggerItem(ItemID.BARROWS_AHRIM_LEGS_75),
            new TriggerItem(ItemID.BARROWS_AHRIM_LEGS_50),
            new TriggerItem(ItemID.BARROWS_AHRIM_LEGS_25),
            new TriggerItem(ItemID.BARROWS_AHRIM_LEGS_BROKEN).fixedCharges(0),

            // Echo ornament kit variants (Leagues V: Raging Echoes).
            new TriggerItem(ItemID.BARROWS_AHRIM_LEGS_ORNAMENT).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_AHRIM_LEGS_ORNAMENT_100),
            new TriggerItem(ItemID.BARROWS_AHRIM_LEGS_ORNAMENT_75),
            new TriggerItem(ItemID.BARROWS_AHRIM_LEGS_ORNAMENT_50),
            new TriggerItem(ItemID.BARROWS_AHRIM_LEGS_ORNAMENT_25),
            new TriggerItem(ItemID.BARROWS_AHRIM_LEGS_ORNAMENT_BROKEN).fixedCharges(0),
        };
    }
}