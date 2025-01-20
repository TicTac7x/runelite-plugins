package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Store;

public class U_CoalBag extends ChargedItemWithStorage {
    public U_CoalBag(
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
        super(TicTac7xChargesImprovedConfig.coal_bag, ItemID.COAL_BAG_12019, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        this.storage = storage
            .storableItems(new StorableItem(ItemID.COAL).checkName("Coal"))
            .setMaximumTotalQuantity(27)
            .setMaximumTotalQuantityWithEquippedItem(36, ItemID.SMITHING_CAPE, ItemID.HITPOINTS_CAPET);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.COAL_BAG_12019),
            new TriggerItem(ItemID.OPEN_COAL_BAG),
        };
        this.triggers = new TriggerBase[] {
            // Check or empty.
            new OnChatMessage("The coal bag is( now)? empty.").emptyStorage(),

            // Fill or check with 1 coal.
            new OnChatMessage("The coal bag( still)? contains one piece of coal.").consumer(() -> {
                storage.put(ItemID.COAL, 1);
            }),

            // Check or empty with not enough inventory space.
            new OnChatMessage("The coal bag( still)? contains (?<charges>.+) pieces of coal.").matcherConsumer((m) -> {
                storage.put(ItemID.COAL, Integer.parseInt(m.group("charges")));
            }),

            // Mine coal with open bag.
            // Extra coal mined by celestial ring.
            // Extra coal mined by varrock platebody.
            new OnChatMessage(
                "(You manage to mine some coal.|Your Celestial ring allows you to mine an additional ore.|The Varrock platebody enabled you to mine an additional ore.)"
            ).onMenuOption("Mine").onMenuTarget("Coal rocks").requiredItem(ItemID.OPEN_COAL_BAG).consumer(() -> {
                storage.add(ItemID.COAL, 1);
            }),

            // Superheat spells.
            new OnXpDrop(Skill.SMITHING).onMenuOption("Cast").onMenuTarget(
                "Superheat Item -> Lovakite ore",
                "Superheat Item -> Iron ore"
            ).consumer(() -> {
                storage.removeAndPrioritizeInventory(ItemID.COAL, 2);
            }),
            new OnXpDrop(Skill.SMITHING).onMenuOption("Cast").onMenuTarget(
                "Superheat Item -> Mithril ore"
            ).consumer(() -> {
                storage.removeAndPrioritizeInventory(ItemID.COAL, 4);
            }),
            new OnXpDrop(Skill.SMITHING).onMenuOption("Cast").onMenuTarget(
                "Superheat Item -> Adamantite ore"
            ).consumer(() -> {
                storage.removeAndPrioritizeInventory(ItemID.COAL, 6);
            }),
            new OnXpDrop(Skill.SMITHING).onMenuOption("Cast").onMenuTarget(
                "Superheat Item -> Runite ore"
            ).consumer(() -> {
                storage.removeAndPrioritizeInventory(ItemID.COAL, 8);
            }),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),
        };
    }
}
