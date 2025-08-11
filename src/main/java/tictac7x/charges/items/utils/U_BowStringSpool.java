package tictac7x.charges.items.utils;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnVarbitChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.ids.VarbitId;

public class U_BowStringSpool extends ChargedItem {
    public U_BowStringSpool(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.bow_string_spool, ItemId.BOW_STRING_SPOOL, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BOW_STRING_SPOOL)
        };

        this.triggers = new TriggerBase[]{
            new OnVarbitChanged(VarbitId.BOW_STRING_SPOOL_CHARGES).setDynamically(),
        };
    }
}
