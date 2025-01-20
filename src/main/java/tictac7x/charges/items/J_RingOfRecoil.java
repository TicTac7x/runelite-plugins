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

public class J_RingOfRecoil extends ChargedItem {
    public J_RingOfRecoil(
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
        super(TicTac7xChargesImprovedConfig.ring_of_recoil, ItemID.RING_OF_RECOIL, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.RING_OF_RECOIL).needsToBeEquipped(),
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("You can inflict one more point of damage before a ring will shatter.").setFixedCharges(1),

            // Check.
            new OnChatMessage("You can inflict (?<charges>.+) more points of damage before a ring will shatter.").setDynamicallyCharges(),

            // Trying to break when full.
            new OnChatMessage("The ring is fully charged. There would be no point in breaking it.").onItemClick().setFixedCharges(40),

            // Shattered.
            new OnChatMessage("Your Ring of Recoil has shattered.").notification().setFixedCharges(40),

            // Take damage.
            new OnHitsplatApplied(HitsplatTarget.SELF).moreThanZeroDamage().isEquipped().decreaseCharges(1),

            // Check from break dialog.
            new OnWidgetLoaded(219, 1, 0).text("Status: (?<charges>.+) damage points? left.").setDynamically(),

            // Break.
            new OnChatMessage("The ring shatters. Your next ring of recoil will start afresh from (?<charges>.+) damage points?.").setDynamicallyCharges(),
        };
    }
}
