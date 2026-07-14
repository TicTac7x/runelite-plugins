package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class VeracsHelm extends _BarrowsItem {
    public VeracsHelm(Provider provider) {
        super("Verac's helmet", ItemID.BARROWS_VERAC_HEAD, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_VERAC_HEAD).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_VERAC_HEAD_100),
            new TriggerItem(ItemID.BARROWS_VERAC_HEAD_75),
            new TriggerItem(ItemID.BARROWS_VERAC_HEAD_50),
            new TriggerItem(ItemID.BARROWS_VERAC_HEAD_25),
            new TriggerItem(ItemID.BARROWS_VERAC_HEAD_BROKEN).fixedCharges(0)
        };
    }
}