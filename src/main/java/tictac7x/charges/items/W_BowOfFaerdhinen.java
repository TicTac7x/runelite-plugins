package tictac7x.charges.items;

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
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Store;

public class W_BowOfFaerdhinen extends ChargedItem {
    public W_BowOfFaerdhinen(
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
        super(TicTac7xChargesImprovedConfig.bow_of_faerdhinen, ItemId.BOW_OF_FAERDHINEN, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED).fixedCharges(Charges.UNLIMITED),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED_ITHELL).fixedCharges(Charges.UNLIMITED),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED_IORWERTH).fixedCharges(Charges.UNLIMITED),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED_TRAHAEARN).fixedCharges(Charges.UNLIMITED),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED_CADARN).fixedCharges(Charges.UNLIMITED),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED_CRWYS).fixedCharges(Charges.UNLIMITED),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED_MEILYR).fixedCharges(Charges.UNLIMITED),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED_AMLODD).fixedCharges(Charges.UNLIMITED),
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your bow of Faerdhinen has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(1888).isEquipped().decreaseCharges(1),
        };
    }
}
