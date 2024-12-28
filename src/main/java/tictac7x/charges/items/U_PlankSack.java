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
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.AdvancedMenuEntry;
import tictac7x.charges.store.Store;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static tictac7x.charges.store.ItemContainerId.INVENTORY;

public class U_PlankSack extends ChargedItemWithStorage {
    private final int REGULAR_PLANK_EXPERIENCE = 29;
    private final int OAK_PLANK_EXPERIENCE = 60;
    private final int TEAK_PLANK_EXPERIENCE = 90;
    private final int MAHOGANY_PLANK_EXPERIENCE = 140;

    public U_PlankSack(
        final Client client,
        final ClientThread client_thread,
        final ConfigManager configs,
        final ItemManager items,
        final InfoBoxManager infoboxes,
        final ChatMessageManager chat_messages,
        final Notifier notifier,
        final ChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        super(ChargesImprovedConfig.plank_sack, ItemID.PLANK_SACK, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);
        storage.maximumTotalQuantity(28).storeableItems(
            new StorageItem(ItemID.PLANK).checkName("Regular plank"),
            new StorageItem(ItemID.OAK_PLANK).checkName("Oak plank"),
            new StorageItem(ItemID.TEAK_PLANK).checkName("Teak plank"),
            new StorageItem(ItemID.MAHOGANY_PLANK).checkName("Mahogany plank")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.PLANK_SACK),
            new TriggerItem(ItemID.PLANK_SACK_25629),
        };

        this.triggers = new TriggerBase[]{
            // Empty.
            new OnChatMessage("Your sack is empty.").emptyStorage(),

            // Check.
            new OnChatMessage("Basic planks: (?<regular>.+), Oak planks: (?<oak>.+), Teak planks: (?<teak>.+), Mahogany planks: (?<mahogany>.+)").matcherConsumer(m -> {
                storage.clear();
                storage.put(ItemID.PLANK, Integer.parseInt(m.group("regular")));
                storage.put(ItemID.OAK_PLANK, Integer.parseInt(m.group("oak")));
                storage.put(ItemID.TEAK_PLANK, Integer.parseInt(m.group("teak")));
                storage.put(ItemID.MAHOGANY_PLANK, Integer.parseInt(m.group("mahogany")));
            }),

            // Empty to inventory.
            new OnItemContainerChanged(INVENTORY).emptyStorageToInventory().onMenuOption("Empty"),

            // Fill from inventory.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventoryAll().onMenuOption("Fill"),


            // Hallowed Sepulchre
            new OnXpDrop(Skill.CONSTRUCTION).xpAmountConsumer((xp) -> {
                storage.removeAndPrioritizeInventory(ItemID.MAHOGANY_PLANK, 2);
            }).onMenuOptionId(
                39527
            ),

            // Mahogany homes - 1 plank
            new OnXpDrop(Skill.CONSTRUCTION).xpAmountConsumer((xp) -> {
                storage.removeAndPrioritizeInventory(getPlankIdBasedOnXpAndPlanks(xp, 1), 1);
            }).onMenuOptionId(
                39982, // Bob clock
                40010, // Leela mirror
                40295, // Tau hat stand
                40099, // Larry clock
                40298, // Larry hat stand
                40289, // Mariah hat stand
                40168, // Ross hat stand
                40170, // Ross mirror
                39995, // Jeff mirror
                39996, // Jeff chair
                40011, // Barbara clock
                40014, // Barbara chair (1)
                40015, // Barbara chair (2)
                40158, // Noella hat stand
                40159, // Noella mirror
                40163, // Noella clock
                40089, // Norman clock
                40177  // Jess clock
            ),

            // Mahogany homes - 2 planks
            new OnXpDrop(Skill.CONSTRUCTION).xpAmountConsumer((xp) -> {
                storage.removeAndPrioritizeInventory(getPlankIdBasedOnXpAndPlanks(xp, 2), 2);
            }).onMenuOptionId(
                39985, // Bob bookcase (1)
                39986, // Bob bookcase (2)
                39983, // Bob cabinet (1)
                39984, // Bob cabinet (2)
                39987, // Bob wardrobe
                39988, // Bob drawers
                40007, // Leela small table (1)
                40008, // Leela small table (2)
                40292, // Leela cupboard
                40086, // Tau cupboard
                40087, // Tau shelves (1)
                40088, // Tau shelves (2)
                40095, // Larry drawers (1)
                40096, // Larry drawers (2)
                40003, // Mariah shelves
                40004, // Mariah bed
                40005, // Mariah small table (1)
                40006, // Mariah small table (2)
                40288, // Mariah cupboard
                40165, // Ross drawers (1)
                40166, // Ross drawers (2)
                40169, // Ross bed
                39990, // Jeff bookcase
                39991, // Jeff shelves
                39993, // Jeff drawers
                39994, // Jeff dresser
                40013, // Barbara bed
                40294, // Barbara drawers
                40156, // Noella dresser
                40157, // Noella cupboard
                40160, // Noella drawers
                40092, // Norman bookshelf
                40093, // Norman drawers
                40094, // Norman small table
                39998, // Sarah bed
                39999, // Sarah dresser
                40000, // Sarah small table
                40001, // Sarah shelves
                40171, // Jess drawers (1)
                40172, // Jess drawers (2)
                40173, // Jess cabinet (1)
                40174  // Jess cabinet (2)
            ),

            // Mahogany homes - 3 planks
            new OnXpDrop(Skill.CONSTRUCTION).xpAmountConsumer((xp) -> {
                storage.removeAndPrioritizeInventory(getPlankIdBasedOnXpAndPlanks(xp, 3), 3);
            }).onMenuOptionId(
                40009, // Leela table
                40291, // Leela double bed
                40084, // Tau table (1)
                40085, // Tau table (2)
                40097, // Larry table (1)
                40098, // Larry table (2)
                40002, // Mariah table
                40167, // Ross double bed
                39989, // Jeff table
                39992, // Jeff bed
                40012, // Barbara table
                40161, // Noella table (1)
                40162, // Noella table (2)
                40090, // Norman table
                40091, // Norman double bed
                39997, // Sarah table
                40175, // Jess bed
                40176  // Jess table
            ),

            // Mahogany homes - 4 planks
            new OnXpDrop(Skill.CONSTRUCTION).xpAmountConsumer((xp) -> {
                storage.removeAndPrioritizeInventory(getPlankIdBasedOnXpAndPlanks(xp, 4), 4);
            }).onMenuOptionId(
                39981  // Bob large table
            )
        };
    }

    private Optional<Integer> getPlankIdBasedOnXpAndPlanks(final int xp, final int planks) {
        // Regular planks
        if (
            xp >= 20 && xp <= 23 && planks == 1 ||
            xp >= 120 && xp <= 133 && planks == 1 ||
            xp >= 44 && xp <= 48 && planks == 2 ||
            xp >= 67 && xp <= 70 && planks == 3 ||
            xp >= 88 && xp <= 92 && planks == 4
        ) {
            return Optional.of(ItemID.PLANK);

        // Oak planks
        } else if (
            xp >= 48 && xp <= 51 && planks == 1 ||
            xp >= 160 && xp <= 165 && planks == 1 ||
            xp >= 96 && xp <= 100 && planks == 2 ||
            xp >= 144 && xp <= 148 && planks == 3 ||
            xp >= 192 && xp <= 198 && planks == 4
        ) {
            return Optional.of(ItemID.OAK_PLANK);

        // Teak planks
        } else if (
            xp >= 71 && xp <= 75 && planks == 1 ||
            xp >= 190 && xp <= 195 && planks == 1 ||
            xp >= 144 && xp <= 149 && planks == 2 ||
            xp >= 216 && xp <= 222 && planks == 3 ||
            xp >= 216 && xp <= 297 && planks == 4
        ) {
            return Optional.of(ItemID.TEAK_PLANK);

        // Mahogany planks
        } else if (
            xp >= 112 && xp <= 115 && planks == 1 ||
            xp >= 240 && xp <= 247 && planks == 1 ||
            xp >= 224 && xp <= 230 && planks == 2 ||
            xp >= 336 && xp <= 345 && planks == 3 ||
            xp >= 448 && xp <= 460 && planks == 4
        ) {
            return Optional.of(ItemID.MAHOGANY_PLANK);
        }

        return Optional.empty();
    }
}
