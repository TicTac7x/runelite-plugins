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
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Store;

public class J_GiantsoulAmulet extends ChargedItem {
    public J_GiantsoulAmulet(
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
        super(TicTac7xChargesImprovedConfig.giantsoul_amulet, ItemId.GIANTSOUL_AMULET, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.GIANTSOUL_AMULET_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.GIANTSOUL_AMULET),
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your Giantsoul amulet has (?<charges>.+) charges? left powering it.").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You add .+ charges? to your Giantsoul amulet, giving it a total of (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Teleport.
            new OnGraphicChanged(3226).decreaseCharges(1),

            // Auto-charge.
            new OnChatMessage("The banker charges your Giantsoul amulet using (?<bigbones>.+)x Big bones.*").matcherConsumer(m -> {
                final int bigBones = Integer.parseInt(m.group("bigbones"));
                increaseCharges(bigBones);
            }),

            // Unified menu entry.
            new OnMenuEntryAdded("Rub").replaceOption("Teleport"),
        };
    }
}
