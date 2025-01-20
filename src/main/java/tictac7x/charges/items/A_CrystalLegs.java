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
        super(TicTac7xChargesImprovedConfig.crystal_legs, ItemID.CRYSTAL_LEGS, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.CRYSTAL_LEGS),
            new TriggerItem(ItemID.CRYSTAL_LEGS_27701),
            new TriggerItem(ItemID.CRYSTAL_LEGS_27713),
            new TriggerItem(ItemID.CRYSTAL_LEGS_27725),
            new TriggerItem(ItemID.CRYSTAL_LEGS_27737),
            new TriggerItem(ItemID.CRYSTAL_LEGS_27749),
            new TriggerItem(ItemID.CRYSTAL_LEGS_27761),
            new TriggerItem(ItemID.CRYSTAL_LEGS_27773),
            new TriggerItem(ItemID.CRYSTAL_LEGS_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_LEGS_INACTIVE_27703).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_LEGS_INACTIVE_27715).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_LEGS_INACTIVE_27727).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_LEGS_INACTIVE_27739).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_LEGS_INACTIVE_27751).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_LEGS_INACTIVE_27763).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_LEGS_INACTIVE_27775).fixedCharges(0)
        };

        this.triggers = new TriggerBase[]{
            new OnChatMessage("Your crystal legs has (?<charges>.+) charges? remaining").setDynamicallyCharges().onItemClick(),
            new OnHitsplatApplied(SELF).isEquipped().decreaseCharges(1)
        };
    }
}
