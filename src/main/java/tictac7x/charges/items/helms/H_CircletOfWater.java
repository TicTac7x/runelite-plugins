package tictac7x.charges.items.helms;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class H_CircletOfWater extends ChargedItem {
    public H_CircletOfWater(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.circlet_of_water, ItemId.CIRCLET_OF_WATER, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CIRCLET_OF_WATER_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.CIRCLET_OF_WATER).needsToBeEquipped(),
        };

        this.triggers = new TriggerBase[] {
            // Protect from heat.
            new OnChatMessage("Your circlet protects you from the desert heat.").decreaseCharges(1),

            // Check.
            new OnChatMessage("Your circlet has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Charge while empty.
            new OnChatMessage("You add (?<charges>.+) charges? to your circlet.$").setDynamicallyCharges(),

            // Charge while not empty.
            new OnChatMessage("You add .+ charges? to your circlet. It now has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Auto-charge.
            new OnChatMessage("The banker charges your Circlet of water using (?<waterrune>.+)x Water rune.").matcherConsumer(m -> {
                final int waterRunes = Integer.parseInt(m.group("waterrune"));
                increaseCharges(waterRunes / 5);
            }),
        };
    }
}
