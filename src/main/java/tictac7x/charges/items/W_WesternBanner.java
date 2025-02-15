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
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Store;

public class W_WesternBanner extends ChargedItem {
    public W_WesternBanner(
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
        super(TicTac7xChargesImprovedConfig.western_banner, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.WESTERN_BANNER_3),
            new TriggerItem(ItemID.WESTERN_BANNER_4).fixedCharges(Charges.UNLIMITED),
        };

        this.triggers = new TriggerBase[]{
            // Teleport.
            new OnMenuOptionClicked("Teleport").hasItemId(ItemID.WESTERN_BANNER_3).setFixedCharges(0),

            // Teleport already used.
            new OnChatMessage("You have already used your available teleports for today. Try again tomorrow after the standard has recharged.").onItemClick().setFixedCharges(0),

            // Daily reset.
            new OnResetDaily(ItemID.WESTERN_BANNER_3).setFixedCharges(1),
        };
    }
}
