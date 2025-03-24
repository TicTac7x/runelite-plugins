package tictac7x.storage.storage;

import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import tictac7x.storage.utils.WidgetId;

import java.util.*;

public class InventoryStorage extends Storage {
    private final Client client;
    private final Storage bankStorage;

    private Map<Integer, Integer> inventoryItemsBefore = new LinkedHashMap<>();

    public InventoryStorage(final String configKey, final int itemContainerId, final Client client, final ItemManager itemManager, final ConfigManager configManager, final Storage bankStorage) {
        super(configKey, itemContainerId, itemManager, configManager);
        this.client = client;
        this.bankStorage = bankStorage;
    }

    private List<Item> getItemsDifference(final ItemContainerChanged event) {
        final List<Item> itemsDifference = new ArrayList<>();

        for (final Integer beforeItemId : inventoryItemsBefore.keySet()) {
            final int beforeItemQuantity = inventoryItemsBefore.get(beforeItemId);
            final int newItemQuantity = event.getItemContainer().count(beforeItemId);

            if (newItemQuantity - beforeItemQuantity != 0) {
                itemsDifference.add(new Item(beforeItemId, newItemQuantity - beforeItemQuantity));
            }
        }

        return itemsDifference;
    }

    private Map<Integer, Integer> getInventoryQuantityMap(final ItemContainerChanged event) {
        final Map<Integer, Integer> inventoryItemsMap = new LinkedHashMap<>();

        for (final Item item : event.getItemContainer().getItems()) {
            if (item.getId() == -1) continue;

            if (!inventoryItemsMap.containsKey(item.getId())) {
                inventoryItemsMap.put(item.getId(), event.getItemContainer().count(item.getId()));
                }
        }

        return inventoryItemsMap;
    }
}
