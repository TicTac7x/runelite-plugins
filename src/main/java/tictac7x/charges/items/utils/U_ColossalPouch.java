package tictac7x.charges.items.utils;

import net.runelite.api.*;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.*;

import java.awt.*;
import java.util.*;
import java.util.List;

import static tictac7x.charges.TicTac7xChargesImprovedPlugin.*;
public class U_ColossalPouch extends ChargedItemWithStorageEmptyable {
    public U_ColossalPouch(Provider provider) {
        super(TicTac7xChargesImprovedConfig.colossal_pouch, ItemID.RCU_POUCH_COLOSSAL, provider);
        this.storage = storage.storableItems(
            new StorableItem (ItemID.BLANKRUNE),
            new StorableItem (ItemID.BLANKRUNE_HIGH),
            new StorableItem (ItemID.BLANKRUNE_DAEYALT),
            new StorableItem (ItemID.GOTR_GUARDIAN_ESSENCE)
        ).setMaximumTotalQuantity(40).setHoldsSingleType(true);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.RCU_POUCH_COLOSSAL),
            new TriggerItem(ItemID.RCU_POUCH_COLOSSAL_DEGRADE),
        };

        this.triggers.addAll(List.of(
            // Empty.
            new OnChatMessage("There is no essence in this pouch.").emptyStorage(),

            // Guardians of the rift.
            new OnChatMessage("The rift becomes active!").consumer(() -> {
                storage.put (ItemID.GOTR_GUARDIAN_ESSENCE, 0);
            }),
            new OnVarbitChanged(13691, 0).consumer(() -> {
                storage.put (ItemID.GOTR_GUARDIAN_ESSENCE, 0);
            }),

            // Check.
            new OnChatMessage("There (is|are) (?<quantity>.+?) (?<essence>normal|pure|daeyalt|guardian|normal) essences? in this pouch.").matcherConsumer((m) -> {
                int quantity = getNumberFromWordRepresentation(m.group("quantity"));

                int essenceId;
                switch (m.group("essence")) {
                    case "normal":
                        essenceId = ItemID.BLANKRUNE;
                        break;
                    case "pure":
                        essenceId = ItemID.BLANKRUNE_HIGH;
                        break;
                    case "daeyalt":
                        essenceId = ItemID.BLANKRUNE_DAEYALT;
                        break;
                    case "guardian":
                        essenceId = ItemID.GOTR_GUARDIAN_ESSENCE;
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
                if (provider.store.inventoryContainsItem (ItemID.GOTR_GUARDIAN_ESSENCE)) {
                    storage.add(ItemID.GOTR_GUARDIAN_ESSENCE, provider.store.getInventoryItemQuantity(ItemID.GOTR_GUARDIAN_ESSENCE));
                } else if (provider.store.inventoryContainsItem (ItemID.BLANKRUNE_DAEYALT)) {
                    storage.add(ItemID.BLANKRUNE_DAEYALT, provider.store.getInventoryItemQuantity(ItemID.BLANKRUNE_DAEYALT));
                } else if (provider.store.inventoryContainsItem (ItemID.BLANKRUNE_HIGH)) {
                    storage.add(ItemID.BLANKRUNE_HIGH, provider.store.getInventoryItemQuantity(ItemID.BLANKRUNE_HIGH));
                } else if (provider.store.inventoryContainsItem (ItemID.BLANKRUNE)) {
                    storage.add(ItemID.BLANKRUNE, provider.store.getInventoryItemQuantity(ItemID.BLANKRUNE));
                }
            }),

            // Use essence on pouch.
            new OnMenuOptionClicked("Use").menuOptionConsumer(advancedMenuEntry -> {
                Optional<StorageItem> essence = getStorageItemFromName(advancedMenuEntry.target, 0);
                if (essence.isPresent()) {
                    essence.get().setQuantity(provider.store.getInventoryItemQuantity(essence.get().itemId));
                    provider.store.nextTickQueue.add(() -> storage.add(essence));
                }
            }).onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Empty to inventory.
            new OnMenuOptionClicked("Empty").runConsumerOnNextGameTick(() -> {
                storage.emptyToInventoryWithoutItemContainerChanged();
            }),

            // Empty to inventory at bank.
            new OnItemContainerChanged(InventoryID.INV).onMenuOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToInventory).emptyStorageToInventory(),

            // Fill from inventory at bank.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onMenuOption(TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory),

            // Replace "Fill" with proper Fill/Empty option.
            new OnMenuEntryAdded("Fill").replaceOptionConsumer(() -> getMenuOptionForUse()).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),
            new OnMenuEntryAdded("Fill").replaceOptionConsumer(() -> getMenuOptionForUse()).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Set maximum charges on level up
            new OnStatChanged(Skill.RUNECRAFT).consumer(() -> {
                storage.setMaximumTotalQuantity(getPouchCapacity());
            })
        ));
    }

    private String getMenuOptionForUse() {
        return (storage.isStorableItemInInventory() || storage.isEmpty())
            ? TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory
            : TicTac7xChargesImprovedPlugin.menuOptionEmptyToInventory;
    }

    @Override
    public Color getTextColor(int itemId) {
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

    private int[] CAPACITY_85 = {40, 35, 30, 25, 20, 15, 10, 5};
    private int[] CAPACITY_75 = {27, 23, 20, 16, 13, 10, 6, 3};
    private int[] CAPACITY_50 = {16, 14, 12, 10, 8, 6, 4, 2};
    private int[] CAPACITY_25 = {8, 5, 2}; // TODO: verify these

    public int getPouchCapacity() {
        int decayCount = provider.config.getColossalPouchDecayCount();
        int runecraftLevel = provider.client.getRealSkillLevel(Skill.RUNECRAFT);

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
