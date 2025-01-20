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
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class W_PharaohsSceptre extends ChargedItem {
    public W_PharaohsSceptre(
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
        super(TicTac7xChargesImprovedConfig.pharaohs_sceptre, ItemID.PHARAOHS_SCEPTRE, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_9045),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_9046),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_9047),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_9048),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_9049),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_9050),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_9051),

            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_13074),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_13075),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_13076),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_13077),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_13078),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_16176),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_21445),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_21446),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_26948),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_26950),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_UNCHARGED).fixedCharges(0),
        };
        
        this.triggers = new TriggerBase[]{
            // Check and automatic messages.
            new OnChatMessage("Your sceptre has (?<charges>.+) charges? left.").setDynamicallyCharges().onItemClick(),
            // Charge non-empty sceptre.
            new OnChatMessage("Right, you already had .+ charges?, and I don't give discounts. That means .+ artefacts gives you (?<charges>.+) charges?. Now be on your way.").increaseDynamically(),
            // Charge empty sceptre.
            new OnChatMessage("Right, .+ artefacts gives you (?<charges>.+) charges. Now be on your way.").setDynamicallyCharges(),
            // Teleport.
            new OnGraphicChanged(715).onItemClick().decreaseCharges(1),
        };
    }
}
