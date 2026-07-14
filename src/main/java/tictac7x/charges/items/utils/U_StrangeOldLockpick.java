package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class U_StrangeOldLockpick extends ChargedItem {
    public U_StrangeOldLockpick(Provider provider) {
        super(TicTac7xChargesImprovedConfig.strange_old_lockpick, ItemId.STRANGE_OLD_LOCKPICK, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.STRANGE_OLD_LOCKPICK).fixedCharges(50),
            new TriggerItem(ItemId.STRANGE_OLD_LOCKPICK_DEGRADED),
        };

        this.triggers.addAll(List.of(
            new OnChatMessage("Your Strange old lockpick( now)? has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),
            new OnChatMessage("The Strange old lockpick crumbles to dust as you use it one last time.")
        ));
    }
}
