package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.enums.*;
import net.runelite.api.gameval.*;

import java.util.*;

public class J_EfaritaysAid extends ChargedItem {
    private boolean attackedVampyre = false;

    public J_EfaritaysAid(Provider provider) {
        super(TicTac7xChargesImprovedConfig.efaritays_aid, ItemID.VAMPYRE_RING, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.VAMPYRE_RING).needsToBeEquipped()
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your ring has (?<charges>.+) charges? left.").setDynamicallyCharges().onItemClick(),

            // Break.
            new OnChatMessage("The ring shatters. Your next Efaritay's aid ring will start afresh from (?<charges>.+) charges.").setDynamicallyCharges(),

            // Low charges.
            new OnChatMessage("Your ring has 10 charges left.").isEquipped().setFixedCharges(10),

            // Out of charges.
            new OnChatMessage("Your ring crumbles to dust.").setFixedCharges(200),

            // Charges from break dialog.
            new OnWidgetLoaded(219, 1, 0).text("Status: (?<charges>.+) charges? left.").setDynamically().onMenuOption("Break").onMenuTarget("Efaritay's aid"),

            // Attack tier-2 vampyre.
            new OnHitsplatApplied(HitsplatTarget.ENEMY, HitsplatGroup.ALL).hasTargetName(
                // Tier 1
                "Count Draynor",
                "Dessous",
                "Feral Vampyre",
                "Kroy",
                "Vampyre Juvenile",

                // Tier 2
                "Vampyre Juvinate",

                // Tier 3
                "Damien Leucurte",
                "Ranis Drakan",
                "Vanstrom Klause",
                "Vyrewatch Sentinel",
                "Vyrewatch"
            ).isEquipped().decreaseCharges(1).consumer(() -> {
                attackedVampyre = true;
            }),
            new OnAnimationChanged(AnimationID.SKELETON_UPDATE_CHAMPION_ATTACK, AnimationID.GHOST_UPDATE_TENDRILL_ATTACK, AnimationID.ZOMBIE_UPDATE_ATTACK_NORMAL).actorName("null").isEquipped().consumer(() -> {
                if (attackedVampyre) {
                    increaseCharges(1);
                    attackedVampyre = false;
                }
            })
        ));
    }
}
