package tictac7x.charges.infoboxes;

import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.ChargedItemInfoBox;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.ChargesItem;
import tictac7x.charges.triggers.TriggerAnimation;
import tictac7x.charges.triggers.TriggerChatMessage;
import tictac7x.charges.triggers.TriggerItem;
import tictac7x.charges.triggers.TriggerWidget;

public class J_RingOfSuffering extends ChargedItemInfoBox {
    public J_RingOfSuffering(final Client client, final ClientThread client_thread, final ConfigManager configs, final ItemManager items, final InfoBoxManager infoboxes, final ChatMessageManager chat_messages, final ChargesImprovedConfig config, final Plugin plugin) {
        super(ChargesItem.RING_OF_SUFFERING, ItemID.RING_OF_SUFFERING, client, client_thread, configs, items, infoboxes, chat_messages, config, plugin);
        this.config_key = ChargesImprovedConfig.ring_of_suffering;
        this.triggers_items = new TriggerItem[]{
                new TriggerItem(ItemID.RING_OF_SUFFERING).fixedCharges(0),
                new TriggerItem(ItemID.RING_OF_SUFFERING_I).fixedCharges(0),
                new TriggerItem(ItemID.RING_OF_SUFFERING_R),
                new TriggerItem(ItemID.RING_OF_SUFFERING_RI),
        };
        this.triggers_chat_messages = new TriggerChatMessage[]{
                new TriggerChatMessage("Your ring currently has (?<charges>.+) recoil charges remaining. The recoil effect is currently enabled.").onItemClick(),
                new TriggerChatMessage("You load your ring with .+ rings? of recoil. It now has (?<charges>.+) recoil charges."),
        };
    }
}
