package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

import java.util.*;

public class U_SoulBearer extends ChargedItem {
    public U_SoulBearer(Provider provider) {
        super(TicTac7xChargesImprovedConfig.soul_bearer, ItemID.ARCEUUS_SOULBEARER, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.ARCEUUS_SOULBEARER),
            new TriggerItem(ItemID.ARCEUUS_SOULBEARER_DAMAGED).fixedCharges(0),
        };

        this.triggers.addAll(List.of(
            // Uncharge.
            new OnChatMessage("You remove the runes from the soul bearer.").setFixedCharges(0),

            // Check.
            new OnChatMessage("(The|Your) soul bearer( now)? has (?<charges>.+) charges.").setDynamicallyCharges(),

            // Check.
            new OnChatMessage("(The|Your) soul bearer( now)? has one charge.").setFixedCharges(1),

            // Charge.
            new OnChatMessage("You add .+ charges? to your soul bearer. It now has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Charge used.
            new OnChatMessage("Your soul bearer carries the ensouled heads to your bank. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Last charge used.
            new OnChatMessage("Your soul bearer carries the ensouled heads to your bank. It has run out of charges.").setFixedCharges(0),

            // Auto-charge
            new OnAutoChargeMessage("Soul bearer", "Blood rune", 1, this)
        ));
    }
}
