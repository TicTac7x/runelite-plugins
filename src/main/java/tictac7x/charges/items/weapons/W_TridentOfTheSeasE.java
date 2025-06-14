package tictac7x.charges.items.weapons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class W_TridentOfTheSeasE extends ChargedItem {
    public W_TridentOfTheSeasE(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.trident_of_the_seas_e, ItemId.TRIDENT_OF_THE_SEAS_ENCHANTED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TRIDENT_OF_THE_SEAS_ENCHANTED_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.TRIDENT_OF_THE_SEAS_ENCHANTED),
        };

        this.triggers = new TriggerBase[] {
            // Ran out of charges.
            new OnChatMessage("Your Trident of the seas \\(e\\) has run out of charges.").setFixedCharges(0),

            // Check, one charge left.
            new OnChatMessage("Your Trident of the seas \\(e\\) has one charge.").setFixedCharges(1),

            // Check for charges and warning when low.
            new OnChatMessage("Your Trident of the seas \\(e\\)( only)? has (?<charges>.+) charges( left)?.").setDynamicallyCharges(),

            // Charge to full.
            new OnChatMessage("You add .* charges? to the Trident of the seas \\(e\\). New total: (?<charges>.+)").setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(1251).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnChatMessage("The banker charges your Trident of the seas (e) using (?<deathrune>.+)x Death rune.*").matcherConsumer(m -> {
                final int deathRunes = Integer.parseInt(m.group("deathrune"));
                increaseCharges(deathRunes);
            }),
        };
    }
}
