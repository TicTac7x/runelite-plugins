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
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class J_RingOfCelestial extends ChargedItem {
    public J_RingOfCelestial(
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
        super(TicTac7xChargesImprovedConfig.celestial_ring, ItemID.CELESTIAL_RING, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.CELESTIAL_RING_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.CELESTIAL_SIGNET_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.CELESTIAL_RING).needsToBeEquipped(),
            new TriggerItem(ItemID.CELESTIAL_SIGNET).needsToBeEquipped()
        };

        this.triggers = new TriggerBase[] {
            // Charge.
            new OnChatMessage("You add .+ charges? to your Celestial (ring|signet). It now has (?<charges>.+) charges?.").setDynamicallyCharges(),
            new OnChatMessage("You add (?<charges>.+) charges? to your Celestial (ring|signet).").setDynamicallyCharges(),

            // Check.
            new OnChatMessage("Your Celestial (ring|signet) has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Ran out of charges.
            new OnChatMessage("Your Celestial (ring|signet) has run out of charges.").notification().setFixedCharges(0),

            // Mine.
            new OnChatMessage("You manage to (mine|quarry) some (clay|copper|tin|guardian fragments|guardian essence|tephra|blurite|limestone|iron|silver|coal|sandstone|gold|granite|mithril|lovakite|adamantite|soft clay)( ore)?.").isEquipped().decreaseCharges(1),
        };
    }
}