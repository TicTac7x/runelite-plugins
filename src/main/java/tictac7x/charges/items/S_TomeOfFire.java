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
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class S_TomeOfFire extends ChargedItem {
    public S_TomeOfFire(
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
        super(ChargesImprovedConfig.tome_of_fire, ItemID.TOME_OF_FIRE, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.TOME_OF_FIRE_EMPTY).fixedCharges(0),
            new TriggerItem(ItemID.TOME_OF_FIRE).needsToBeEquipped(),
        };

        this.triggers = new TriggerBase[] {
            // Check searing pages.
            new OnChatMessage("Your tome has been charged with Searing Pages. It currently holds (?<charges>.+) charges?.").setDynamically().onItemClick(),

            // Check burnt pages.
            new OnChatMessage("Your tome has been charged with Burnt Pages. It currently holds (?<charges>.+) charges?.").setDynamically().onItemClick(),

            // Attack with regular spellbook fire spells.
            new OnGraphicChanged(99, 126, 129, 155, 1464).isEquipped().decreaseCharges(1)
        };
    }
}
