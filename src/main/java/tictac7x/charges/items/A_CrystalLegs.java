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
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

import static tictac7x.charges.store.HitsplatTarget.SELF;

public class A_CrystalLegs extends ChargedItem {
    public A_CrystalLegs(
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
        super(TicTac7xChargesImprovedConfig.crystal_legs, ItemId.CRYSTAL_LEGS, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CRYSTAL_LEGS),
            new TriggerItem(ItemId.CRYSTAL_LEGS_HEFIN),
            new TriggerItem(ItemId.CRYSTAL_LEGS_ITHELL),
            new TriggerItem(ItemId.CRYSTAL_LEGS_IORWERTH),
            new TriggerItem(ItemId.CRYSTAL_LEGS_TRAHAEARN),
            new TriggerItem(ItemId.CRYSTAL_LEGS_CADARN),
            new TriggerItem(ItemId.CRYSTAL_LEGS_CRWYS),
            new TriggerItem(ItemId.CRYSTAL_LEGS_AMLODD),
            new TriggerItem(ItemId.CRYSTAL_LEGS_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_LEGS_HEFIN_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_LEGS_ITHELL_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_LEGS_IORWERTH_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_LEGS_TRAHAEARN_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_LEGS_CADARN_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_LEGS_CRWYS_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_LEGS_AMLODD_INACTIVE).fixedCharges(0),
        };

        this.triggers = new TriggerBase[]{
            new OnChatMessage("Your crystal legs has (?<charges>.+) charges? remaining").setDynamicallyCharges().onItemClick(),
            new OnHitsplatApplied(SELF).isEquipped().decreaseCharges(1)
        };
    }
}
