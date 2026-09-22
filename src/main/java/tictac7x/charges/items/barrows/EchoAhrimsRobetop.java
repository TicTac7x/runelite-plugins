package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.ItemID;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class EchoAhrimsRobetop extends _BarrowsItem {
    public EchoAhrimsRobetop(Provider provider) {
        super("Echo Ahrim's body", ItemID.BARROWS_AHRIM_BODY_ORNAMENT, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_AHRIM_BODY_ORNAMENT).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_AHRIM_BODY_ORNAMENT_100),
            new TriggerItem(ItemID.BARROWS_AHRIM_BODY_ORNAMENT_75),
            new TriggerItem(ItemID.BARROWS_AHRIM_BODY_ORNAMENT_50),
            new TriggerItem(ItemID.BARROWS_AHRIM_BODY_ORNAMENT_25),
            new TriggerItem(ItemID.BARROWS_AHRIM_BODY_ORNAMENT_BROKEN).fixedCharges(0),
        };
    }
}