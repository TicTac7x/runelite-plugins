package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ItemId;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.store.Store;
import tictac7x.charges.store.VarbitId;

public class C_MagicCape extends ChargedItem {
    public C_MagicCape(
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
        super(TicTac7xChargesImprovedConfig.magic_cape, ItemId.MAGIC_CAPE, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.MAGIC_CAPE),
            new TriggerItem(ItemId.MAGIC_CAPE_TRIMMED)
        };

        this.triggers = new TriggerBase[] {
            new OnVarbitChanged(VarbitId.MAGIC_CAPE_CHARGES_USED).varbitValueConsumer(chargesUsed -> setCharges(5 - chargesUsed)),
        };
    }
}
