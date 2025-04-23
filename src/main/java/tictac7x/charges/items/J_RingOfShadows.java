package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import tictac7x.charges.item.triggers.OnAnimationChanged;
import tictac7x.charges.store.AnimationId;
import tictac7x.charges.store.ItemId;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class J_RingOfShadows extends ChargedItem {
    public J_RingOfShadows(
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
        super(TicTac7xChargesImprovedConfig.ring_of_shadows, ItemId.RING_OF_SHADOWS, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.RING_OF_SHADOWS_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.RING_OF_SHADOWS)
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your ring of shadows has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You add (?<charges>.+) charges? to the ring of shadows.$").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You add .+ charges? to the ring of shadows. It now has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Teleport.
            new OnAnimationChanged(AnimationId.RING_OF_SHADOWS_TELEPORT).decreaseCharges(1),

            // Auto-charge.
            new OnChatMessage("The banker charges your Ring of shadows using (?<bloodrune>.+)x Blood rune.*").matcherConsumer(m -> {
                final int bloodRunes = Integer.parseInt(m.group("ringofrecoil"));
                increaseCharges(bloodRunes);
            }),
        };
    }
}
