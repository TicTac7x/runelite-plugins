package tictac7x.charges.items.utils;

import tictac7x.charges.store.*;
import net.runelite.api.Skill;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.ItemContainerId;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.ids.WidgetId;

import java.awt.*;
import java.util.Optional;

import static tictac7x.charges.TicTac7xChargesImprovedPlugin.getNumberFromWordRepresentation;
import static tictac7x.charges.store.ids.ItemContainerId.INVENTORY;

public class U_ColossalPouch extends ChargedItemWithStorage {
    public U_ColossalPouch(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.colossal_pouch, ItemId.COLOSSAL_POUCH, provider);
        this.storage = storage.storableItems(
            new StorableItem(ItemId.RUNE_ESSENCE),
            new StorableItem(ItemId.PURE_ESSENCE),
            new StorableItem(ItemId.DAEYALT_ESSENCE),
            new StorableItem(ItemId.GUARDIAN_ESSENCE)
        ).setMaximumTotalQuantity(40).setHoldsSingleType(true);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.COLOSSAL_POUCH),
            new TriggerItem(ItemId.COLOSSAL_POUCH_DEGRADED),
        };

        this.triggers = new TriggerBase[]{
            // Empty.
            new OnChatMessage("There is no essence in this pouch.").emptyStorage(),

            // Guardians of the rift.
            new OnChatMessage("The rift becomes active!").consumer(() -> {
                storage.put(ItemId.GUARDIAN_ESSENCE, 0);
            }),
            new OnVarbitChanged(13691, 0).consumer(() -> {
                storage.put(ItemId.GUARDIAN_ESSENCE, 0);
            }),

            // Check.
            new OnChatMessage("There (is|are) (?<quantity>.+?) (?<essence>normal|pure|daeyalt|guardian|normal) essences? in this pouch.").matcherConsumer((m) -> {
                final int quantity = getNumberFromWordRepresentation(m.group("quantity"));

                int essenceId;
                switch (m.group("essence")) {
                    case "normal":
                        essenceId = ItemId.RUNE_ESSENCE;
                        break;
                    case "pure":
                        essenceId = ItemId.PURE_ESSENCE;
                        break;
                    case "daeyalt":
                        essenceId = ItemId.DAEYALT_ESSENCE;
                        break;
                    case "guardian":
                        essenceId = ItemId.GUARDIAN_ESSENCE;
                        break;
                    default:
                        return;
                }

                storage.clearAndPut(essenceId, quantity);
            }).onMenuOption("Check"),

            // Decay.
            new OnChatMessage("Your pouch has decayed through use.").onMenuOption("Fill").consumer(() -> {
                provider.configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.colossal_pouch_decay_count, provider.config.getColossalPouchDecayCount() + 1);
                storage.setMaximumTotalQuantity(getPouchCapacity());
            }),

            // Repair.
            new OnChatMessage("Fine. A simple transfiguration spell should resolve things for you.").consumer(() -> {
                provider.configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.colossal_pouch_decay_count, 0);
                storage.setMaximumTotalQuantity(getPouchCapacity());
            }),

            // Fill from inventory.
            new OnMenuOptionClicked("Fill").runConsumerOnNextGameTick(() -> {
                if (provider.store.inventoryContainsItem(ItemId.GUARDIAN_ESSENCE)) {
                    storage.add(ItemId.GUARDIAN_ESSENCE, provider.store.getInventoryItemQuantity(ItemId.GUARDIAN_ESSENCE));
                } else if (provider.store.inventoryContainsItem(ItemId.DAEYALT_ESSENCE)) {
                    storage.add(ItemId.DAEYALT_ESSENCE, provider.store.getInventoryItemQuantity(ItemId.DAEYALT_ESSENCE));
                } else if (provider.store.inventoryContainsItem(ItemId.PURE_ESSENCE)) {
                    storage.add(ItemId.PURE_ESSENCE, provider.store.getInventoryItemQuantity(ItemId.PURE_ESSENCE));
                } else if (provider.store.inventoryContainsItem(ItemId.RUNE_ESSENCE)) {
                    storage.add(ItemId.RUNE_ESSENCE, provider.store.getInventoryItemQuantity(ItemId.RUNE_ESSENCE));
                }
            }),

            // Use essence on pouch.
            new OnMenuOptionClicked("Use").menuOptionConsumer(advancedMenuEntry -> {
                final Optional<StorageItem> essence = getStorageItemFromName(advancedMenuEntry.target, 0);
                if (essence.isPresent()) {
                    essence.get().setQuantity(provider.store.getInventoryItemQuantity(essence.get().getId()));
                    provider.store.nextTickQueue.add(() -> storage.add(essence));
                }
            }).onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Empty to inventory.
            new OnMenuOptionClicked("Empty").runConsumerOnNextGameTick(() -> {
                storage.emptyToInventoryWithoutItemContainerChanged();
            }),

            // Empty to inventory at bank.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).onMenuOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToInventory).emptyStorageToInventory(),

            // Fill from inventory at bank.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onMenuOption(TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory),

            // Replace "Fill" with proper Fill/Empty option.
            new OnMenuEntryAdded("Fill").replaceOptionConsumer(() -> getMenuOptionForUse()).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),
            new OnMenuEntryAdded("Fill").replaceOptionConsumer(() -> getMenuOptionForUse()).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Set maximum charges on level up
            new OnStatChanged(Skill.RUNECRAFT).consumer(() -> {
                storage.setMaximumTotalQuantity(getPouchCapacity());
            }),
        };
    }

    private String getMenuOptionForUse() {
        return (storage.isStorableItemInInventory() || storage.isEmpty())
            ? TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory
            : TicTac7xChargesImprovedPlugin.menuOptionEmptyToInventory;
    }

    @Override
    public Color getTextColor(final int itemId) {
        return getTotalTextColor();
    }

    @Override
    public Color getTotalTextColor() {
        if (storage.isFull()) {
            if (provider.config.getColossalPouchDecayCount() == 0) {
                return provider.config.getColorActivated();
            } else {
                return provider.config.getColorEmpty();
            }
        }

        return super.getTotalTextColor();
    }

    private final int[] CAPACITY_85 = {40, 35, 30, 25, 20, 15, 10, 5};
    private final int[] CAPACITY_75 = {27, 23, 20, 16, 13, 10, 6, 3};
    private final int[] CAPACITY_50 = {16, 14, 12, 10, 8, 6, 4, 2};
    private final int[] CAPACITY_25 = {8, 5, 2}; // TODO: verify these

    public int getPouchCapacity() {
        final int decayCount = provider.config.getColossalPouchDecayCount();
        final int runecraftLevel = provider.client.getRealSkillLevel(Skill.RUNECRAFT);

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
