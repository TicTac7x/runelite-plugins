package tictac7x.charges.infoboxes;

import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.ChargedItemInfoBox;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.store.ChargesItem;
import tictac7x.charges.store.Store;
import tictac7x.charges.triggers.TriggerChatMessage;
import tictac7x.charges.triggers.TriggerHitsplat;
import tictac7x.charges.triggers.TriggerItem;

public class A_CrystalBody extends ChargedItemInfoBox {
    public A_CrystalBody(
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
        super(ChargesItem.CRYSTAL_BODY, ItemID.CRYSTAL_BODY, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, plugin);
        this.config_key = ChargesImprovedConfig.ring_of_suffering;
        this.triggers_items = new TriggerItem[]{
                new TriggerItem(ItemID.CRYSTAL_BODY),
                new TriggerItem(ItemID.CRYSTAL_BODY_INACTIVE).fixedCharges(0)
        };
        this.triggers_chat_messages = new TriggerChatMessage[]{
                new TriggerChatMessage("Your crystal body has (?<charges>.+) charges? remaining").onItemClick()
        };
        this.triggers_hitsplats = new TriggerHitsplat[]{
                new TriggerHitsplat(1).equipped().onSelf()
        };
    }
}
