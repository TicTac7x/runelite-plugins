package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

import java.util.*;

public class U_StrangeOldLockpick extends ChargedItem {
    public U_StrangeOldLockpick(Provider provider) {
        super(TicTac7xChargesImprovedConfig.strange_old_lockpick, ItemID.STRANGE_OLD_LOCKPICK_FULL, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.STRANGE_OLD_LOCKPICK_FULL).fixedCharges(50),
            new TriggerItem(ItemID.STRANGE_OLD_LOCKPICK),
        };

        this.triggers.addAll(List.of(
            new OnChatMessage("Your Strange old lockpick( now)? has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),
            new OnChatMessage("The Strange old lockpick crumbles to dust as you use it one last time.")
        ));
    }
}
