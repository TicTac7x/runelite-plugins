package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.CombatStyle;
import tictac7x.charges.store.HitsplatGroup;
import tictac7x.charges.store.HitsplatTarget;
import tictac7x.charges.store.Store;

public class J_EfaritaysAid extends ChargedItem {
    public J_EfaritaysAid(
        final Client client,
        final ClientThread clientThread,
        final ConfigManager configManager,
        final ItemManager itemManager,
        final InfoBoxManager infoBoxManager,
        final ChatMessageManager chatMessageManager,
        final Notifier notifier,
        final TicTac7xChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        super(TicTac7xChargesImprovedConfig.efaritays_aid, ItemID.EFARITAYS_AID, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.EFARITAYS_AID).needsToBeEquipped()
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
                ItemID.BLESSED_AXE,
                ItemID.IVANDIS_FLAIL,
                ItemID.BLISTERWOOD_FLAIL,
                ItemID.SILVER_SICKLE,
                ItemID.SILVER_SICKLE_B,
                ItemID.EMERALD_SICKLE_B,
                ItemID.ENCHANTED_EMERALD_SICKLE_B,
                ItemID.RUBY_SICKLE_B,
                ItemID.ENCHANTED_RUBY_SICKLE_B,
                ItemID.BLISTERWOOD_SICKLE,
                ItemID.SILVERLIGHT,
                ItemID.DARKLIGHT,
                ItemID.EMBERLIGHT,
                ItemID.ROD_OF_IVANDIS_1,
                ItemID.ROD_OF_IVANDIS_2,
                ItemID.ROD_OF_IVANDIS_3,
                ItemID.ROD_OF_IVANDIS_4,
                ItemID.ROD_OF_IVANDIS_5,
                ItemID.ROD_OF_IVANDIS_6,
                ItemID.ROD_OF_IVANDIS_7,
                ItemID.ROD_OF_IVANDIS_8,
                ItemID.ROD_OF_IVANDIS_9,
                ItemID.ROD_OF_IVANDIS_10,
                ItemID.WOLFBANE
            ).decreaseCharges(1),

            // Additional charge used with silver bolts on successful hit.
            new OnHitsplatApplied(HitsplatTarget.ENEMY, HitsplatGroup.ALL).hasTargetName("Vampyre Juvinate").combatStyle(CombatStyle.RANGED).isEquipped().multiTrigger().itemEquipped(
                ItemID.SILVER_BOLTS
            ).decreaseCharges(1),
        };
    }
}
