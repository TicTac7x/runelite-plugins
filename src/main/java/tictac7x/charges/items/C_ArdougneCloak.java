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
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnResetDaily;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.ItemKey;
import tictac7x.charges.store.Store;

public class C_ArdougneCloak extends ChargedItem {
    public C_ArdougneCloak(
        final Client client,
        final ClientThread client_thread,
        final ConfigManager configs,
        final ItemManager items,
        final InfoBoxManager infoboxes,
        final ChatMessageManager chat_messages,
        final Notifier notifier,
        final ChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        super(ChargesImprovedConfig.ardougne_cloak, ItemKey.ARDOUGNE_CLOAK, ItemID.ARDOUGNE_CLOAK, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.ARDOUGNE_CLOAK_2),
            new TriggerItem(ItemID.ARDOUGNE_CLOAK_3),
            new TriggerItem(ItemID.ARDOUGNE_CLOAK_4).fixedCharges(Charges.UNLIMITED)
        };

        this.triggers = new TriggerBase[] {
            new OnChatMessage("You have used (?<used>.+) of your (?<total>.+) Ardougne Farm teleports for today.").useDifference(),
            new OnResetDaily(3).specificItem(ItemID.ARDOUGNE_CLOAK_2),
            new OnResetDaily(5).specificItem(ItemID.ARDOUGNE_CLOAK_3),
        };
    }
}
