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
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class S_DragonfireShield extends ChargedItem {
    public S_DragonfireShield(
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
        super(TicTac7xChargesImprovedConfig.dragonfire_shield, ItemID.DRAGONFIRE_SHIELD, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.DRAGONFIRE_SHIELD_11284).fixedCharges(0),
            new TriggerItem(ItemID.DRAGONFIRE_SHIELD),
            new TriggerItem(ItemID.DRAGONFIRE_WARD_22003).fixedCharges(0),
            new TriggerItem(ItemID.DRAGONFIRE_WARD)
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("The shield has (?<charges>.+) charges?.").setDynamicallyCharges().onItemClick(),

            // Uncharge.
            new OnChatMessage("You vent the shield's remaining charges harmlessly into the air.").setFixedCharges(0),

            // Charge collected.
            new OnChatMessage("Your dragonfire (shield|ward) glows more brightly.").increaseCharges(1),

            // Already full.
            new OnChatMessage("Your dragonfire shield is already fully charged.").setFixedCharges(50),

            // Attack.
            new OnGraphicChanged(1165).isEquipped().decreaseCharges(1),
        };
    }
}
