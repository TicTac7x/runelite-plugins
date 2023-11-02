package tictac7x.charges.items;

import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.ItemKey;
import tictac7x.charges.store.Store;
import tictac7x.charges.item.triggers.TriggerItem;

public class W_IbansStaff extends ChargedItem {
    public W_IbansStaff(
        final Client client,
        final ClientThread client_thread,
        final ConfigManager configs,
        final ItemManager items,
        final InfoBoxManager infoboxes,
        final ChatMessageManager chat_messages,
        final Notifier notifier,
        final ChargesImprovedConfig config,
        final Store store,
        final Plugin plugin
    ) {
        super(ItemKey.IBANS_STAFF, ItemID.IBANS_STAFF, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store);
        this.config_key = ChargesImprovedConfig.ibans_staff;
        this.triggersItems = new TriggerItem[]{
            new TriggerItem(ItemID.IBANS_STAFF),
            new TriggerItem(ItemID.IBANS_STAFF_1410),
            new TriggerItem(ItemID.IBANS_STAFF_U),
        };
        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("You have (?<charges>.+) charges left on the staff.").setDynamically().onItemClick(),
            // Attack.
            new OnGraphicChanged(87).isEquipped().decreaseCharges(1),
        };
    }
}
