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
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Store;

public class J_Camulet extends ChargedItem {
    public J_Camulet(
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
        super(TicTac7xChargesImprovedConfig.camulet, ItemID.CAMULET, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.CAMULET),
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your Camulet has one charge left.").setFixedCharges(1),
            new OnChatMessage("Your Camulet has (?<charges>.+) charges left.").setDynamicallyCharges(),

            // Recharge.
            new OnChatMessage("You recharge the Camulet using camel dung. Yuck!").setFixedCharges(4),

            // Trying to charge fully charged.
            new OnChatMessage("The Camulet is already fully charged.").setFixedCharges(4),

            // Unlimited charges.
            new OnChatMessage("The Camulet has unlimited charges.").setFixedCharges(Charges.UNLIMITED),

            // Replace check.
            new OnMenuEntryAdded("Check-charge").replaceOption("Check"),
        };
    }
}
