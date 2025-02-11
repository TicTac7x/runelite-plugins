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

public class J_RingOfTheElements extends ChargedItem {
    public J_RingOfTheElements(
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
        super(TicTac7xChargesImprovedConfig.ring_of_the_elements, ItemID.RING_OF_THE_ELEMENTS, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.RING_OF_THE_ELEMENTS),
            new TriggerItem(ItemID.RING_OF_THE_ELEMENTS_26818),
        };

        this.triggers = new TriggerBase[] {
            // Teleport.
            new OnVarbitChanged(13707).setDynamically(),

            // Unified menu entry.
            new OnMenuEntryAdded("Rub").replaceOption("Teleport"),

            // Last destination replaced with actual altar.
            new OnMenuEntryAdded("Last Destination").replaceOption("Air Altar").replaceTarget("Ring of the elements", "").varbitCheck(13708, 1),
            new OnMenuEntryAdded("Last Destination").replaceOption("Water Altar").replaceTarget("Ring of the elements", "").varbitCheck(13708, 2),
            new OnMenuEntryAdded("Last Destination").replaceOption("Earth Altar").replaceTarget("Ring of the elements", "").varbitCheck(13708, 3),
            new OnMenuEntryAdded("Last Destination").replaceOption("Fire Altar").replaceTarget("Ring of the elements", "").varbitCheck(13708, 4),
        };
    }
}