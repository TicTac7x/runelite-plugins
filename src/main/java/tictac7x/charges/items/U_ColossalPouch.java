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
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Store;

import java.awt.*;
import java.util.Optional;

import static tictac7x.charges.TicTac7xChargesImprovedPlugin.getNumberFromWordRepresentation;

public class U_ColossalPouch extends ChargedItemWithStorage {
    public U_ColossalPouch(
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
        super(TicTac7xChargesImprovedConfig.colossal_pouch, ItemID.COLOSSAL_POUCH, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        this.storage = storage.storableItems(
            new StorableItem(ItemID.RUNE_ESSENCE),
            new StorableItem(ItemID.PURE_ESSENCE),
            new StorableItem(ItemID.DAEYALT_ESSENCE),
            new StorableItem(ItemID.GUARDIAN_ESSENCE)
        ).setMaximumTotalQuantity(40).setHoldsSingleType(true);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.COLOSSAL_POUCH),
            new TriggerItem(ItemID.COLOSSAL_POUCH_26786), // Degraded
        };

        this.triggers = new TriggerBase[]{
            // Empty.
            new OnChatMessage("There is no essence in this pouch.").emptyStorage(),

            // Guardians of the rift.
            new OnChatMessage("The rift becomes active!").consumer(() -> {
                storage.put(ItemID.GUARDIAN_ESSENCE, 0);
            }),
            new OnVarbitChanged(13691, 0).consumer(() -> {
                storage.put(ItemID.GUARDIAN_ESSENCE, 0);
            }),

            // Check.
            new OnChatMessage("There (is|are) (?<quantity>.+?) (?<essence>normal|pure|daeyalt|guardian|normal) essences? in this pouch.").matcherConsumer((m) -> {
                final int quantity = getNumberFromWordRepresentation(m.group("quantity"));

                int essenceId;
                switch (m.group("essence")) {
                    case "normal":
                        essenceId = ItemID.RUNE_ESSENCE;
                        break;
                    case "pure":
                        essenceId = ItemID.PURE_ESSENCE;
                        break;
                    case "daeyalt":
                        essenceId = ItemID.DAEYALT_ESSENCE;
                        break;
                    case "guardian":
                        essenceId = ItemID.GUARDIAN_ESSENCE;
                        break;
                    default:
                        return;
                }

                storage.clearAndPut(essenceId, quantity);
            }).onMenuOption("Check"),

            // Decay.
            new OnChatMessage("Your pouch has decayed through use.").onMenuOption("Fill").consumer(() -> {
                configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.colossal_pouch_decay_count, config.getColossalPouchDecayCount() + 1);
                storage.setMaximumTotalQuantity(getPouchCapacity());
            }),

            // Repair.
            new OnChatMessage("Fine. A simple transfiguration spell should resolve things for you.").consumer(() -> {
                configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.colossal_pouch_decay_count, 0);
                storage.setMaximumTotalQuantity(getPouchCapacity());
            }),

            // Fill from inventory.
            new OnMenuOptionClicked("Fill").runConsumerOnNextGameTick(() -> {
                if (store.inventoryContainsItem(ItemID.GUARDIAN_ESSENCE)) {
                    storage.add(ItemID.GUARDIAN_ESSENCE, store.getInventoryItemQuantity(ItemID.GUARDIAN_ESSENCE));
                } else if (store.inventoryContainsItem(ItemID.DAEYALT_ESSENCE)) {
                    storage.add(ItemID.DAEYALT_ESSENCE, store.getInventoryItemQuantity(ItemID.DAEYALT_ESSENCE));
                } else if (store.inventoryContainsItem(ItemID.PURE_ESSENCE)) {
                    storage.add(ItemID.PURE_ESSENCE, store.getInventoryItemQuantity(ItemID.PURE_ESSENCE));
                } else if (store.inventoryContainsItem(ItemID.RUNE_ESSENCE)) {
                    storage.add(ItemID.RUNE_ESSENCE, store.getInventoryItemQuantity(ItemID.RUNE_ESSENCE));
                }
            }),

            // Use essence on pouch.
            new OnMenuOptionClicked("Use").menuOptionConsumer(advancedMenuEntry -> {
                final Optional<StorageItem> essence = getStorageItemFromName(advancedMenuEntry.target);
                if (essence.isPresent()) {
                    store.nextTickQueue.add(() -> storage.add(essence.get().itemId, store.getInventoryItemQuantity(essence.get().itemId)));
                }
            }).onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Empty to inventory.
            new OnMenuOptionClicked("Empty").runConsumerOnNextGameTick(() -> {
                storage.emptyToInventoryWithoutItemContainerChanged();
            }),

            // Set maximum charges on level up
            new OnStatChanged(Skill.RUNECRAFT).consumer(() -> {
                storage.setMaximumTotalQuantity(getPouchCapacity());
            }),
        };
    }

    @Override
    public Color getTextColor() {
        if (storage.isFull()) {
            if (config.getColossalPouchDecayCount() == 0) {
                return config.getColorActivated();
            } else {
                return config.getColorEmpty();
            }
        }

        return super.getTextColor();
    }

    private final int[] CAPACITY_85 = {40, 35, 30, 25, 20, 15, 10, 5};
    private final int[] CAPACITY_75 = {27, 23, 20, 16, 13, 10, 6, 3};
    private final int[] CAPACITY_50 = {16, 14, 12, 10, 8, 6, 4, 2};
    private final int[] CAPACITY_25 = {8, 5, 2}; // TODO: verify these

    public int getPouchCapacity() {
        final int decayCount = config.getColossalPouchDecayCount();
        final int runecraftLevel = this.client.getRealSkillLevel(Skill.RUNECRAFT);

        if (runecraftLevel >= 85) {
            return CAPACITY_85[Math.min(CAPACITY_85.length - 1, decayCount)];
        } else if (runecraftLevel >= 75) {
            return CAPACITY_75[Math.min(CAPACITY_75.length - 1, decayCount)];
        } else if (runecraftLevel >= 50) {
            return CAPACITY_50[Math.min(CAPACITY_50.length - 1, decayCount)];
        } else if (runecraftLevel >= 25) {
            return CAPACITY_25[Math.min(CAPACITY_25.length - 1, decayCount)];
        } else {
            return 0;
        }
    }
}
