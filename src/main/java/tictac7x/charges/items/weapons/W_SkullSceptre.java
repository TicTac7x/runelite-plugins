package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_SkullSceptre extends ChargedItem {
    public W_SkullSceptre(Provider provider) {
        super(TicTac7xChargesImprovedConfig.skull_sceptre, ItemID.SOS_SKULL_SCEPTRE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.SOS_SKULL_SCEPTRE),
            new TriggerItem(ItemID.SOS_SKULL_SCEPTRE_IMBUED)
        };

        this.triggers.addAll(List.of(
            // Empty.
            new OnChatMessage("Your (imbued )?Skull Sceptre has run out of charges. You must use some more sceptre pieces or fragments on it to recharge it.").setFixedCharges(0),

            // Teleport.
            new OnChatMessage("Your Skull Sceptre has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Check.
            new OnChatMessage("Concentrating deeply, you divine that the sceptre has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Charge to maximum.
            new OnChatMessage("You charge the Skull Sceptre with .*. It now contains the maximum number of charges, (?<charges>\\d+).").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You charge the Skull Sceptre with .*. It now contains (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Unified menu entry.
            new OnMenuEntryAdded("Divine").replaceOption("Check"),
            new OnMenuEntryAdded("Invoke").replaceOption("Teleport")
        ));
    }
}
