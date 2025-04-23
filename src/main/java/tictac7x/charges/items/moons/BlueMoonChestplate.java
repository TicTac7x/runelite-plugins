package tictac7x.charges.items.moons;

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
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnCombat;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class BlueMoonChestplate extends _MoonItem {
    public BlueMoonChestplate(
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
        super("blue_chestplate", ItemId.BLUE_MOON_CHESTPLATE, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BLUE_MOON_CHESTPLATE).fixedCharges(3000),
            new TriggerItem(ItemId.BLUE_MOON_CHESTPLATE_DEGRADED),
            new TriggerItem(ItemId.BLUE_MOON_CHESTPLATE_BROKEN).fixedCharges(0),
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("Your Blue moon chestplate has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // In combat.
            new OnCombat(90).isEquipped().decreaseCharges(1),
        };
    }
}