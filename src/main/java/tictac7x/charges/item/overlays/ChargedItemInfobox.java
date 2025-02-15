package tictac7x.charges.item.overlays;

import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBox;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.TriggerItem;

import java.awt.Color;
import java.util.Optional;

public class ChargedItemInfobox extends InfoBox {
    private final ChargedItemBase chargedItem;
    private final ItemManager itemManager;
    private final InfoBoxManager infoBoxManager;
    private final TicTac7xChargesImprovedConfig config;
    private final ConfigManager configManager;

    private int itemId;

    public ChargedItemInfobox(
        final ChargedItemBase chargedItem,
        final ItemManager itemManager,
        final InfoBoxManager infoBoxManager,
        final ConfigManager configManager,
        final TicTac7xChargesImprovedConfig config,
        final TicTac7xChargesImprovedPlugin plugin
    ) {
        super(itemManager.getImage(chargedItem.items[0] != null ? chargedItem.items[0].itemId : -1), plugin);
        this.chargedItem = chargedItem;
        this.itemManager = itemManager;
        this.infoBoxManager = infoBoxManager;
        this.configManager = configManager;
        this.config = config;
        this.itemId = chargedItem.items[0] != null ? chargedItem.items[0].itemId : -1;
    }

    @Override
    public String getName() {
        return super.getName() + "_" + chargedItem.configKey;
    }

    @Override
    public String getText() {
        return chargedItem.getCharges(itemId);
    }

    @Override
    public String getTooltip() {
        return chargedItem.getTooltip(itemId);
    }

    @Override
    public Color getTextColor() {
        return chargedItem.getTextColor(itemId);
    }

    @Override
    public boolean render() {
        if (
            !config.showInfoboxes() ||
            !isChargedItemInfoboxEnabled() ||
            !config.showUnlimited() && chargedItem.getCharges(itemId).equals("∞") ||
            (!chargedItem.inInventory() && !chargedItem.inEquipment())
        ) {
            return false;
        }

        return true;
    }

    private void updateItem(final int itemId) {
        this.itemId = itemId;
        setImage(itemManager.getImage(itemId));
        infoBoxManager.updateInfoBoxImage(this);
    }

    private boolean isChargedItemInfoboxEnabled() {
        final Optional<String> visible = Optional.ofNullable(configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, chargedItem.configKey + "_infobox"));

        if (visible.isPresent() && visible.get().equals("false")) {
            return false;
        }

        return true;
    }

    public void onItemContainerChanged(final ItemContainerChanged event) {
        if (event.getContainerId() != InventoryID.INVENTORY.getId() && event.getContainerId() != InventoryID.EQUIPMENT.getId()) return;

        Optional<Integer> inventoryId = Optional.empty();
        Optional<Integer> equipmentId = Optional.empty();

        if (chargedItem.store.inventory.isPresent()) {
            for (final Item inventoryItem : chargedItem.store.inventory.get().getItems()) {
                for (final TriggerItem triggerItem : chargedItem.items) {
                    if (inventoryItem.getId() == triggerItem.itemId) {
                        if (!inventoryId.isPresent() || !triggerItem.fixedCharges.isPresent()) {
                            inventoryId = Optional.of(triggerItem.itemId);
                        }
                    }
                }
            }
        }

        if (chargedItem.store.equipment.isPresent()) {
            for (final Item equipmentItem : chargedItem.store.equipment.get().getItems()) {
                for (final TriggerItem triggerItem : chargedItem.items) {
                    if (equipmentItem.getId() == triggerItem.itemId) {
                        if (!equipmentId.isPresent() || !triggerItem.fixedCharges.isPresent()) {
                            equipmentId = Optional.of(triggerItem.itemId);
                        }
                    }
                }
            }
        }

        if (equipmentId.isPresent() && equipmentId.get() != itemId) {
            updateItem(equipmentId.get());
        } else if (inventoryId.isPresent() && inventoryId.get() != itemId) {
            updateItem(inventoryId.get());
        }
    }
}


