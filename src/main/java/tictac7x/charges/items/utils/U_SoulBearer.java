package tictac7x.charges.items.utils;

import tictac7x.charges.item.triggers.OnAutoChargeMessage;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.util.List;

public class U_SoulBearer extends ChargedItem {
    public U_SoulBearer(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.soul_bearer, ItemId.SOUL_BEARER, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.SOUL_BEARER),
            new TriggerItem(ItemId.SOUL_BEARER_UNCHARGED).fixedCharges(0),
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
