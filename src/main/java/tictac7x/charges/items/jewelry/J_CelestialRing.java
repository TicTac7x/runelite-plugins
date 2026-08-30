package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

import java.util.*;

public class J_CelestialRing extends ChargedItem {
    public J_CelestialRing(Provider provider) {
        super(TicTac7xChargesImprovedConfig.celestial_ring, ItemID.CELESTIAL_RING_CHARGED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.CELESTIAL_RING).fixedCharges(0),
            new TriggerItem(ItemID.CELESTIAL_SIGNET).fixedCharges(0),
            new TriggerItem(ItemID.CELESTIAL_RING_CHARGED).needsToBeEquipped(),
            new TriggerItem(ItemID.CELESTIAL_SIGNET_CHARGED).needsToBeEquipped()
        };

        this.triggers.addAll(List.of(
            // Charge.
            new OnChatMessage("You add .+ charges? to your Celestial (ring|signet). It now has (?<charges>.+) charges?.").setDynamicallyCharges(),
            new OnChatMessage("You add (?<charges>.+) charges? to your Celestial (ring|signet).").setDynamicallyCharges(),

            // Check.
            new OnChatMessage("Your Celestial (ring|signet) has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Ran out of charges.
            new OnChatMessage("Your Celestial (ring|signet) has run out of charges.").setFixedCharges(0),

            // Mine.
            new OnChatMessage("You manage to (mine|quarry) some (clay|copper|tin|guardian fragments|guardian essence|tephra|blurite|limestone|iron|silver|lead|coal|sandstone|gold|granite|rubium|sunstone|mithril|amalgamation|lovakite|adamantite|soft clay)( ore)?.").isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnChatMessage("The banker charges your Celestial (ring|signet) using (?<stardust>.+)x Stardust.").matcherConsumer(m -> {
                int stardust = Integer.parseInt(m.group("stardust"));
                increaseCharges(stardust);
            })
        ));
    }
}