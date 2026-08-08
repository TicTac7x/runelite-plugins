package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class AhrimsHood extends _BarrowsItem {
    public AhrimsHood(Provider provider) {
        super("Ahrim's hood", ItemID.BARROWS_AHRIM_HEAD, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_100),
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_75),
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_50),
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_25),
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_BROKEN).fixedCharges(0),

            // Echo ornament kit variants (Leagues V: Raging Echoes).
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_ORNAMENT).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_ORNAMENT_100),
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_ORNAMENT_75),
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_ORNAMENT_50),
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_ORNAMENT_25),
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_ORNAMENT_BROKEN).fixedCharges(0),
        };
    }
}