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

public class J_BurningAmulet extends ChargedItem {
    public J_BurningAmulet(
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
        super(TicTac7xChargesImprovedConfig.burning_amulet, ItemID.BURNING_AMULET1, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BURNING_AMULET1).fixedCharges(1),
            new TriggerItem(ItemID.BURNING_AMULET2).fixedCharges(2),
            new TriggerItem(ItemID.BURNING_AMULET3).fixedCharges(3),
            new TriggerItem(ItemID.BURNING_AMULET4).fixedCharges(4),
            new TriggerItem(ItemID.BURNING_AMULET5).fixedCharges(5),
        };

        this.triggers = new TriggerBase[]{
            new OnMenuEntryAdded("Rub").replaceOption("Teleport"),
        };
    }
}