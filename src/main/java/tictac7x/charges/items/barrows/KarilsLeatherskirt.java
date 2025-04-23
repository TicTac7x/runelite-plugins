package tictac7x.charges.items.barrows;

import com.google.gson.Gson;
import net.runelite.api.Client;
import tictac7x.charges.store.ItemId;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class KarilsLeatherskirt extends _BarrowsItem {
    public KarilsLeatherskirt(
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
        super("Karil's skirt", ItemId.KARILS_LEATHERSKIRT, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.KARILS_LEATHERSKIRT).fixedCharges(1000),
            new TriggerItem(ItemId.KARILS_LEATHERSKIRT_100),
            new TriggerItem(ItemId.KARILS_LEATHERSKIRT_75),
            new TriggerItem(ItemId.KARILS_LEATHERSKIRT_50),
            new TriggerItem(ItemId.KARILS_LEATHERSKIRT_25),
            new TriggerItem(ItemId.KARILS_LEATHERSKIRT_0).fixedCharges(0)
        };
    }
}
