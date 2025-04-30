package tictac7x.charges.items.capes;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.VarbitId;

public class C_MagicCape extends ChargedItem {
    public C_MagicCape(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.magic_cape, ItemId.MAGIC_CAPE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.MAGIC_CAPE),
            new TriggerItem(ItemId.MAGIC_CAPE_TRIMMED)
        };

        this.triggers = new TriggerBase[] {
            new OnVarbitChanged(VarbitId.MAGIC_CAPE_CHARGES_USED).varbitValueConsumer(chargesUsed -> setCharges(5 - chargesUsed)),
        };
    }
}
