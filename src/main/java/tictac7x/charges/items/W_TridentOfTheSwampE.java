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

public class W_TridentOfTheSwampE extends ChargedItem {
    public W_TridentOfTheSwampE(
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
        super(TicTac7xChargesImprovedConfig.trident_of_the_swamp_e, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.UNCHARGED_TOXIC_TRIDENT_E).fixedCharges(0),
            new TriggerItem(ItemID.TRIDENT_OF_THE_SWAMP_E),
        };

        this.triggers = new TriggerBase[] {
            // Ran out of charges.
            new OnChatMessage("Your Trident of the swamp \\(e\\) has run out of charges.").notification().setFixedCharges(0),

            // Check, one charge left.
            new OnChatMessage("Your Trident of the swamp \\(e\\) has one charge.").setFixedCharges(1),

            // Check for charges and warning when low.
            new OnChatMessage("Your Trident of the swamp \\(e\\)( only)? has (?<charges>.+) charges( left)?.").setDynamicallyCharges(),

            // Charge to full.
            new OnChatMessage("You add .* charges? to the Trident of the swamp \\(e\\). New total: (?<charges>.+)").setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(1251).isEquipped().decreaseCharges(1)
        };
    }
}
