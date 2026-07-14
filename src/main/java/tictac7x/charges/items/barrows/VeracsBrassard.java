package tictac7x.charges.items.barrows;

import tictac7x.charges.store.ids.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class VeracsBrassard extends _BarrowsItem {
    public VeracsBrassard(Provider provider) {
        super("Verac's body", ItemId.VERACS_BRASSARD, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.VERACS_BRASSARD).fixedCharges(1000),
            new TriggerItem(ItemId.VERACS_BRASSARD_100),
            new TriggerItem(ItemId.VERACS_BRASSARD_75),
            new TriggerItem(ItemId.VERACS_BRASSARD_50),
            new TriggerItem(ItemId.VERACS_BRASSARD_25),
            new TriggerItem(ItemId.VERACS_BRASSARD_0).fixedCharges(0)
        };
    }
}