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
import tictac7x.charges.item.triggers.OnResetDaily;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class S_FaladorShield extends ChargedItem {
    public S_FaladorShield(
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
        super(TicTac7xChargesImprovedConfig.falador_shield, ItemID.FALADOR_SHIELD_1, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.FALADOR_SHIELD_1),
            new TriggerItem(ItemID.FALADOR_SHIELD_2),
            new TriggerItem(ItemID.FALADOR_SHIELD_3),
            new TriggerItem(ItemID.FALADOR_SHIELD_4),
        };
        
        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("You have one remaining charge for today.").onItemClick().setFixedCharges(1),
            new OnChatMessage("You have two remaining charges for today.").onItemClick().setFixedCharges(2),

            // Teleport when empty.
            new OnChatMessage("You have already used (both )?your charge(s)? for today.").onItemClick().setFixedCharges(0),
            new OnChatMessage("You have already used all available recharges today. Try again tomorrow when the shield has recharged.").onItemClick().setFixedCharges(0),

            // Recharge prayer.
            new OnGraphicChanged(321).onItemClick().decreaseCharges(1),

            // Daily resets.
            new OnResetDaily().specificItem(ItemID.FALADOR_SHIELD_1).setFixedCharges(1),
            new OnResetDaily().specificItem(ItemID.FALADOR_SHIELD_2).setFixedCharges(1),
            new OnResetDaily().specificItem(ItemID.FALADOR_SHIELD_3).setFixedCharges(1),
            new OnResetDaily().specificItem(ItemID.FALADOR_SHIELD_4).setFixedCharges(2),
        };
    }
}
