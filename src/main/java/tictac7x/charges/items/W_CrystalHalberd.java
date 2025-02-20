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
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

import static tictac7x.charges.store.HitsplatTarget.ENEMY;

public class W_CrystalHalberd extends ChargedItem {
    public W_CrystalHalberd(
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
        super(TicTac7xChargesImprovedConfig.crystal_halberd, ItemID.CRYSTAL_HALBERD, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.CRYSTAL_HALBERD),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_24125),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_110),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_110_I),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_210),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_210_I),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_310),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_310_I),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_410),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_410_I),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_510),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_510_I),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_610),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_610_I),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_710),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_710_I),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_810),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_810_I),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_910),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_910_I),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_FULL),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_FULL_I),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemID.NEW_CRYSTAL_HALBERD_FULL),
            new TriggerItem(ItemID.NEW_CRYSTAL_HALBERD_FULL_I),
            new TriggerItem(ItemID.NEW_CRYSTAL_HALBERD_FULL_16893),
            new TriggerItem(ItemID.NEW_CRYSTAL_HALBERD_FULL_I_16892),
        };
        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("Your crystal halberd has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Attack.
            new OnHitsplatApplied(ENEMY).oncePerGameTick().isEquipped().decreaseCharges(1),
        };
    }
}
