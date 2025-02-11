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

import static tictac7x.charges.store.ItemContainerId.INVENTORY;
import static tictac7x.charges.store.ItemContainerId.BANK;


public class U_GemBag extends ChargedItemWithStorage {
    public U_GemBag(
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
        super(TicTac7xChargesImprovedConfig.gem_bag, ItemID.GEM_BAG_12020, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        storage.setMaximumIndividualQuantity(60).storableItems(
            new StorableItem(ItemID.UNCUT_SAPPHIRE).checkName("Sapphire").specificOrder(1),
            new StorableItem(ItemID.UNCUT_EMERALD).checkName("Emerald").specificOrder(2),
            new StorableItem(ItemID.UNCUT_RUBY).checkName("Ruby").specificOrder(3),
            new StorableItem(ItemID.UNCUT_DIAMOND).checkName("Diamond").specificOrder(4),
            new StorableItem(ItemID.UNCUT_DRAGONSTONE).checkName("Dragonstone").specificOrder(5)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.GEM_BAG_12020),
            new TriggerItem(ItemID.OPEN_GEM_BAG),
        };

        this.triggers = new TriggerBase[]{
            // Empty to bank or inventory.
            new OnChatMessage("The gem bag is( now)? empty.").emptyStorage(),

            // Empty and Check.
            new OnChatMessage("(Left in bag: )?Sapphires: (?<sapphires>.+) / Emeralds: (?<emeralds>.+) / Rubies: (?<rubies>.+) Diamonds: (?<diamonds>.+) / Dragonstones: (?<dragonstones>.+)").matcherConsumer(m -> {
                storage.put(ItemID.UNCUT_SAPPHIRE, Integer.parseInt(m.group("sapphires")));
                storage.put(ItemID.UNCUT_EMERALD, Integer.parseInt(m.group("emeralds")));
                storage.put(ItemID.UNCUT_RUBY, Integer.parseInt(m.group("rubies")));
                storage.put(ItemID.UNCUT_DIAMOND, Integer.parseInt(m.group("diamonds")));
                storage.put(ItemID.UNCUT_DRAGONSTONE, Integer.parseInt(m.group("dragonstones")));
            }),

            // Mining regular or gem rocks.
            new OnChatMessage("You just (found|mined) (a|an) (?<gem>.+)!").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("gem")), 1);
            }).requiredItem(ItemID.OPEN_GEM_BAG),

            // Pickpocketing.
            new OnChatMessage("The following stolen loot gets added to your gem bag: Uncut (?<gem>.+) x (?<quantity>.+).").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("gem")), Integer.parseInt(m.group("quantity")));
            }),

            // Stealing from stalls.
            new OnChatMessage("You steal an uncut (?<gem>.+) and add it to your gem bag.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("gem")), 1);
            }),

            // Fill from inventory.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onMenuOption("Fill"),

            // Empty to bank.
            new OnItemContainerChanged(BANK).emptyStorageToBank().onMenuOption("Empty"),

            // Empty from deposit box.
            new OnMenuOptionClicked("Empty").emptyStorage().isWidgetVisible(192, 1),

            // Use gem on bag
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseChargedItemOnStorageItem(storage.getStorableItems()),
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Pick up.
            new OnItemPickup(storage.getStorableItems()).isByOne().requiredItem(ItemID.OPEN_GEM_BAG).pickUpToStorage(),

            // Telegrab.
            new OnXpDrop(Skill.MAGIC).requiredItem(ItemID.OPEN_GEM_BAG).onMenuOption("Cast").onMenuTarget(
                "Uncut sapphire"
            ).addToStorage(ItemID.UNCUT_SAPPHIRE, 1),
            new OnXpDrop(Skill.MAGIC).requiredItem(ItemID.OPEN_GEM_BAG).onMenuOption("Cast").onMenuTarget(
                "Uncut emerald"
            ).addToStorage(ItemID.UNCUT_EMERALD, 1),
            new OnXpDrop(Skill.MAGIC).requiredItem(ItemID.OPEN_GEM_BAG).onMenuOption("Cast").onMenuTarget(
                "Uncut ruby"
            ).addToStorage(ItemID.UNCUT_RUBY, 1),
            new OnXpDrop(Skill.MAGIC).requiredItem(ItemID.OPEN_GEM_BAG).onMenuOption("Cast").onMenuTarget(
                "Uncut diamond"
            ).addToStorage(ItemID.UNCUT_DIAMOND, 1),
            new OnXpDrop(Skill.MAGIC).requiredItem(ItemID.OPEN_GEM_BAG).onMenuOption("Cast").onMenuTarget(
                "Uncut dragonstone"
            ).addToStorage(ItemID.UNCUT_DRAGONSTONE, 1),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),
        };
    }
}
