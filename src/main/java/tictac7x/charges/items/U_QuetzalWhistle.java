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
import tictac7x.charges.item.triggers.OnAnimationChanged;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnItemContainerChanged;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ItemContainerId;
import tictac7x.charges.store.ItemWithQuantity;
import tictac7x.charges.store.Store;

public class U_QuetzalWhistle extends ChargedItem {
    public U_QuetzalWhistle(
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
        super(TicTac7xChargesImprovedConfig.quetzal_whistle, ItemID.BASIC_QUETZAL_WHISTLE, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BASIC_QUETZAL_WHISTLE).maxCharges(5),
            new TriggerItem(ItemID.ENHANCED_QUETZAL_WHISTLE).maxCharges(20),
            new TriggerItem(ItemID.PERFECTED_QUETZAL_WHISTLE).maxCharges(50),
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your quetzal whistle has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Teleport.
            new OnAnimationChanged(10944).decreaseCharges(1),

            // Teleport menu entry.
            new OnMenuEntryAdded("Signal").replaceOption("Teleport"),

            // Craft basic quetzal whistle.
            new OnChatMessage("You craft yourself a basic quetzal whistle.").setFixedCharges(0),

            // Fully charged.
            new OnChatMessage("Looks like the birds are all full for now. Make them work a bit before feeding them again!").requiredItem(ItemID.BASIC_QUETZAL_WHISTLE).setFixedCharges(5),
            new OnChatMessage("Looks like the birds are all full for now. Make them work a bit before feeding them again!").requiredItem(ItemID.ENHANCED_QUETZAL_WHISTLE).setFixedCharges(20),
            new OnChatMessage("Looks like the birds are all full for now. Make them work a bit before feeding them again!").requiredItem(ItemID.PERFECTED_QUETZAL_WHISTLE).setFixedCharges(50),

            // Partially charged.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).hasChatMessage("Soar Leader Pitri|There you go. Some whistle charges for you!").onItemContainerDifference(itemsDifference -> {
                for (final ItemWithQuantity item : itemsDifference.items) {
                    switch (item.itemId) {
                        case ItemID.QUETZAL_FEED:
                        case ItemID.RAW_WILD_KEBBIT:
                        case ItemID.RAW_BARBTAILED_KEBBIT:
                        case ItemID.RAW_LARUPIA:
                            increaseCharges(Math.abs(item.quantity));
                            break;
                        case ItemID.RAW_GRAAHK:
                        case ItemID.RAW_KYATT:
                        case ItemID.RAW_PYRE_FOX:
                            increaseCharges(Math.abs(item.quantity) * 2);
                            break;
                        case ItemID.RAW_DASHING_KEBBIT:
                        case ItemID.RAW_SUNLIGHT_ANTELOPE:
                        case ItemID.RAW_MOONLIGHT_ANTELOPE:
                            increaseCharges(Math.abs(item.quantity) * 3);
                            break;
                    }
                }
            })
        };
    }
}
