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
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Store;

public class U_TeleportCrystal extends ChargedItem {
    public U_TeleportCrystal(
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
        super(TicTac7xChargesImprovedConfig.teleport_crystal, ItemID.TELEPORT_CRYSTAL, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.TELEPORT_CRYSTAL).fixedCharges(0),
            new TriggerItem(ItemID.TELEPORT_CRYSTAL_1).fixedCharges(1),
            new TriggerItem(ItemID.TELEPORT_CRYSTAL_2).fixedCharges(2),
            new TriggerItem(ItemID.TELEPORT_CRYSTAL_3).fixedCharges(3),
            new TriggerItem(ItemID.TELEPORT_CRYSTAL_4).fixedCharges(4),
            new TriggerItem(ItemID.TELEPORT_CRYSTAL_5).fixedCharges(5),
            new TriggerItem(ItemID.ETERNAL_TELEPORT_CRYSTAL).fixedCharges(Charges.UNLIMITED),
        };
    }
}
