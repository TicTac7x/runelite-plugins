package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.Provider;

import java.util.*;

public class U_BowStringSpool extends ChargedItem {
    public U_BowStringSpool(Provider provider) {
        super(TicTac7xChargesImprovedConfig.bow_string_spool, ItemId.BOW_STRING_SPOOL, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BOW_STRING_SPOOL)
        };

        this.triggers.addAll(List.of(
            new OnVarbitChanged(VarbitId.BOW_STRING_SPOOL_CHARGES).setDynamically()
        ));
    }
}
