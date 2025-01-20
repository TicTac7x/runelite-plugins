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
import tictac7x.charges.store.Store;

public class W_WarpedSceptre extends ChargedItem {
    public W_WarpedSceptre(
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
        super(TicTac7xChargesImprovedConfig.warped_sceptre, ItemID.WARPED_SCEPTRE, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.WARPED_SCEPTRE_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.WARPED_SCEPTRE)
        };

        this.triggers = new TriggerBase[] {
            // Charge additional.
            new OnChatMessage("You add an additional .+ charges? to your warped sceptre. It now has (?<charges>.+) charges in total.").setDynamicallyCharges(),

            // Charge empty.
            new OnChatMessage("You add (?<charges>.+) charges? to your warped sceptre.").setDynamicallyCharges(),

            // Check.
            new OnChatMessage("Your warped sceptre( only)? has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(2567).decreaseCharges(1),

            // Uncharge.
            new OnChatMessage("You uncharge your warped sceptre").setFixedCharges(0),

            // Ran out of charges.
            new OnChatMessage("Your warped sceptre has run out of charges!").setFixedCharges(0),
        };
    }
}
