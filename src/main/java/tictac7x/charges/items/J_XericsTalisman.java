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
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.OnWidgetLoaded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ItemKey;
import tictac7x.charges.store.Store;

public class J_XericsTalisman extends ChargedItem {
    public J_XericsTalisman(
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
        super(ChargesImprovedConfig.xerics_talisman, ItemKey.XERICS_TALISMAN, ItemID.XERICS_TALISMAN, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.XERICS_TALISMAN_INERT).fixedCharges(0),
            new TriggerItem(ItemID.XERICS_TALISMAN),
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("(The|Your) talisman( now)? has one charge.").onItemClick().fixedCharges(1),
            new OnChatMessage("(The|Your) talisman( now)? has (?<charges>.+) charges.").setDynamically().onItemClick(),

            // Teleport.
            new OnGraphicChanged(1612).decreaseCharges(1),

            // Teleport widget.
            new OnWidgetLoaded(187, 0, 1, "The talisman has (?<charges>.+) charges.").setDynamically(),
        };
    }
}
