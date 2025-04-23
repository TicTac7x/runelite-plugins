package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
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
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class W_TumekensShadow extends ChargedItem {
    public W_TumekensShadow(
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
        super(TicTac7xChargesImprovedConfig.tumekens_shadow, ItemId.TUMEKENS_SHADOW, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TUMEKENS_SHADOW_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.TUMEKENS_SHADOW),
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Tumeken's shadow( only)? has (?<charges>.+) charges? (remaining|left)").setDynamicallyCharges(),

            // Uncharge.
            new OnChatMessage("You uncharge your Tumeken's shadow").setFixedCharges(0),

            // Charge.
            new OnChatMessage("You apply (?<charges>.+) to your Tumeken's shadow.").setDynamicallyCharges(),

            // Charge additionally.
            new OnChatMessage("You apply an additional .* charges to your Tumeken's shadow. It now has (?<charges>.+) charges in total.").setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(2125).decreaseCharges(1),

            // Auto-charge.
            new OnChatMessage("The banker charges your Tumeken's shadow using (?<soulrune>.+)x Soul rune").matcherConsumer(m -> {
                final int soulRunes = Integer.parseInt(m.group("soulrune"));
                increaseCharges(soulRunes / 2);
            }),
        };
    }
}
