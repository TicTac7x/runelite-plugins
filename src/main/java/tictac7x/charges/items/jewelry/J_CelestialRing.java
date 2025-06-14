package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class J_CelestialRing extends ChargedItem {
    public J_CelestialRing(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.celestial_ring, ItemId.CELESTIAL_RING, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CELESTIAL_RING_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.CELESTIAL_SIGNET_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.CELESTIAL_RING).needsToBeEquipped(),
            new TriggerItem(ItemId.CELESTIAL_SIGNET).needsToBeEquipped()
        };

        this.triggers = new TriggerBase[] {
            // Charge.
            new OnChatMessage("You add .+ charges? to your Celestial (ring|signet). It now has (?<charges>.+) charges?.").setDynamicallyCharges(),
            new OnChatMessage("You add (?<charges>.+) charges? to your Celestial (ring|signet).").setDynamicallyCharges(),

            // Check.
            new OnChatMessage("Your Celestial (ring|signet) has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Ran out of charges.
            new OnChatMessage("Your Celestial (ring|signet) has run out of charges.").setFixedCharges(0),

            // Mine.
            new OnChatMessage("You manage to (mine|quarry) some (clay|copper|tin|guardian fragments|guardian essence|tephra|blurite|limestone|iron|silver|coal|sandstone|gold|granite|mithril|lovakite|adamantite|soft clay)( ore)?.").isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnChatMessage("The banker charges your Celestial (ring|signet) using (?<stardust>.+)x Stardust.").matcherConsumer(m -> {
                final int stardust = Integer.parseInt(m.group("stardust"));
                increaseCharges(stardust);
            })
        };
    }
}