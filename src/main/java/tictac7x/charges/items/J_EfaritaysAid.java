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
            new OnHitsplatApplied(HitsplatTarget.ENEMY).hasTargetName("Vampyre Juvinate").isEquipped().decreaseCharges(1),
        };
    }
}
