package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.ItemId;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class W_EnchantedLyre extends ChargedItem {
    public W_EnchantedLyre(
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
        super(TicTac7xChargesImprovedConfig.enchanted_lyre, ItemId.ENCHANTED_LYRE_0, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ENCHANTED_LYRE_0).fixedCharges(0),
            new TriggerItem(ItemId.ENCHANTED_LYRE_1).fixedCharges(1),
            new TriggerItem(ItemId.ENCHANTED_LYRE_2).fixedCharges(2),
            new TriggerItem(ItemId.ENCHANTED_LYRE_3).fixedCharges(3),
            new TriggerItem(ItemId.ENCHANTED_LYRE_4).fixedCharges(4),
            new TriggerItem(ItemId.ENCHANTED_LYRE_5).fixedCharges(5),
            new TriggerItem(ItemId.ENCHANTED_LYRE_IMBUED).fixedCharges(Charges.UNLIMITED),
        };

        this.triggers = new TriggerBase[]{
            new OnMenuEntryAdded("Play").replaceOption("Teleport"),
        };
    }
}
