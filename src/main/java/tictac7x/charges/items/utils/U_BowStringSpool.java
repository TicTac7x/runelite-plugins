package tictac7x.charges.items.utils;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.Provider;

import java.util.*;

public class U_BowStringSpool extends ChargedItem {
    public U_BowStringSpool(Provider provider) {
        super(TicTac7xChargesImprovedConfig.bow_string_spool, ItemID.BOWSTRING_SPOOL, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BOWSTRING_SPOOL)
        };

        this.triggers.addAll(List.of(
            new OnVarbitChanged(VarbitID.BOWSTRING_SPOOL_CHARGES).setDynamically()
        ));
    }
}
