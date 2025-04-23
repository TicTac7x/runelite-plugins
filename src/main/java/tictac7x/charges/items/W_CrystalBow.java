package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
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
import tictac7x.charges.item.triggers.OnAnimationChanged;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class W_CrystalBow extends ChargedItem {
    public W_CrystalBow(
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
        super(TicTac7xChargesImprovedConfig.crystal_bow, ItemId.CRYSTAL_BOW, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CRYSTAL_BOW_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_BOW),
            new TriggerItem(ItemId.CRYSTAL_BOW_FULL).fixedCharges(2500),
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your crystal bow has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Attack.
            new OnAnimationChanged(AnimationId.HUMAN_BOW).isEquipped().decreaseCharges(1),
        };
    }
}
