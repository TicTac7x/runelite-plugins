package tictac7x.charges.items.weapons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class W_SkullSceptre extends ChargedItem {
    public W_SkullSceptre(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.skull_sceptre, ItemId.SKULL_SCEPTRE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.SKULL_SCEPTRE),
            new TriggerItem(ItemId.SKULL_SCEPTRE_IMBUED)
        };

        this.triggers = new TriggerBase[] {
            // Teleport.
            new OnChatMessage("Your Skull Sceptre has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Check.
            new OnChatMessage("Concentrating deeply, you divine that the sceptre has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Charge to maximum without all Varrock diaries completed.
            new OnChatMessage("You charge the Skull Sceptre with .*. It now contains the maximum number of charges, (?<charges>.+). Completing tiers of the Varrock achievement diary will allow the imbued Skull Sceptre to hold more charges.").setDynamicallyCharges(),

            // Charge to maximum.
            new OnChatMessage("You charge the Skull Sceptre with .*. It now contains the maximum number of charges, (?<charges>.+).").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You charge the Skull Sceptre with .*. It now contains (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Unified menu entry.
            new OnMenuEntryAdded("Divine").replaceOption("Check"),
            new OnMenuEntryAdded("Invoke").replaceOption("Teleport"),
        };
    }
}
