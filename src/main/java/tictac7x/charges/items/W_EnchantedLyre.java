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
        super(TicTac7xChargesImprovedConfig.enchanted_lyre, ItemID.ENCHANTED_LYRE, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.LYRE).fixedCharges(0),
            new TriggerItem(ItemID.ENCHANTED_LYRE).fixedCharges(0),
            new TriggerItem(ItemID.ENCHANTED_LYRE1).fixedCharges(1),
            new TriggerItem(ItemID.ENCHANTED_LYRE2).fixedCharges(2),
            new TriggerItem(ItemID.ENCHANTED_LYRE3).fixedCharges(3),
            new TriggerItem(ItemID.ENCHANTED_LYRE4).fixedCharges(4),
            new TriggerItem(ItemID.ENCHANTED_LYRE5).fixedCharges(5),
        };

        this.triggers = new TriggerBase[]{
            new OnMenuEntryAdded("Play").replaceOption("Teleport"),
        };
    }
}
