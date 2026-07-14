package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class VeracsBrassard extends _BarrowsItem {
    public VeracsBrassard(Provider provider) {
        super("Verac's body", ItemID.BARROWS_VERAC_BODY, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_VERAC_BODY).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_VERAC_BODY_100),
            new TriggerItem(ItemID.BARROWS_VERAC_BODY_75),
            new TriggerItem(ItemID.BARROWS_VERAC_BODY_50),
            new TriggerItem(ItemID.BARROWS_VERAC_BODY_25),
            new TriggerItem(ItemID.BARROWS_VERAC_BODY_BROKEN).fixedCharges(0)
        };
    }
}