package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.*;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.ItemId;

public class J_EfaritaysAid extends ChargedItem {
    public J_EfaritaysAid(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.efaritays_aid, ItemId.EFARITAYS_AID, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.EFARITAYS_AID).needsToBeEquipped()
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("Your ring has (?<charges>.+) charges? left.").setDynamicallyCharges().onItemClick(),

            // Break.
            new OnChatMessage("The ring shatters. Your next Efaritay's aid ring will start afresh from (?<charges>.+) charges.").setDynamicallyCharges(),

            // Out of charges.
            new OnChatMessage("Your ring crumbles to dust.").setFixedCharges(0),

            // Charges from break dialog.
            new OnWidgetLoaded(219, 1, 0).text("Status: (?<charges>.+) charges? left.").setDynamically().onMenuOption("Break").onMenuTarget("Efaritay's aid"),

            // Attack tier-2 vampyre.
            new OnHitsplatApplied(HitsplatTarget.ENEMY).hasTargetName("Vampyre Juvinate").isEquipped().multiTrigger().decreaseCharges(1),

            // Additional charge used with silver melee weapon on successful hit.
            new OnHitsplatApplied(HitsplatTarget.ENEMY, HitsplatGroup.ALL).hasTargetName("Vampyre Juvinate").combatStyle(CombatStyle.MELEE).isEquipped().multiTrigger().itemEquipped(
                ItemId.BLESSED_AXE,
                ItemId.IVANDIS_FLAIL,
                ItemId.BLISTERWOOD_FLAIL,
                ItemId.SILVER_SICKLE,
                ItemId.SILVER_SICKLE_BLESSED,
                ItemId.EMERALD_SICKLE_BLESSED,
                ItemId.EMERALD_SICKLE_BLESSED_ENCHANTED,
                ItemId.RUBY_SICKLE_BLESSED,
                ItemId.RUBY_SICKLE_BLESSED_ENCHANTED,
                ItemId.BLISTERWOOD_SICKLE,
                ItemId.SILVERLIGHT,
                ItemId.DARKLIGHT,
                ItemId.ARCLIGHT,
                ItemId.ARCLIGHT_UNCHARGED,
                ItemId.EMBERLIGHT,
                ItemId.ROD_OF_IVANDIS_1,
                ItemId.ROD_OF_IVANDIS_2,
                ItemId.ROD_OF_IVANDIS_3,
                ItemId.ROD_OF_IVANDIS_4,
                ItemId.ROD_OF_IVANDIS_5,
                ItemId.ROD_OF_IVANDIS_6,
                ItemId.ROD_OF_IVANDIS_7,
                ItemId.ROD_OF_IVANDIS_8,
                ItemId.ROD_OF_IVANDIS_9,
                ItemId.ROD_OF_IVANDIS_10,
                ItemId.WOLFBANE
            ).decreaseCharges(1),

            // Additional charge used with silver bolts on successful hit.
            new OnHitsplatApplied(HitsplatTarget.ENEMY, HitsplatGroup.ALL).hasTargetName("Vampyre Juvinate").combatStyle(CombatStyle.RANGED).isEquipped().multiTrigger().itemEquipped(
                ItemId.SILVER_BOLTS
            ).decreaseCharges(1),
        };
    }
}
