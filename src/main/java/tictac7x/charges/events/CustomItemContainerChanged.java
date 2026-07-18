package tictac7x.charges.events;

import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.game.*;
import tictac7x.charges.*;
import tictac7x.charges.item.storage.*;

import java.util.*;

public class CustomItemContainerChanged {
    public final int itemContainerId;
    private final List<StorageItem> items;

    public CustomItemContainerChanged(int itemContainerId, List<StorageItem> items) {
        this.itemContainerId = itemContainerId;
        this.items = items;
    }

    public CustomItemContainerChanged(CustomItemContainerChanged previousItemContainerChanged) {
        this.itemContainerId = previousItemContainerChanged.itemContainerId;
        this.items = new ArrayList<>();

        for (StorageItem item : previousItemContainerChanged.getItems()) {
            items.add(new StorageItem(item.itemId, item.getQuantity()));
        }
    }

    public List<StorageItem> getItems() {
        return items;
    }

    public int size() {
        return items.size();
    }

    public int getContainerId() {
        return itemContainerId;
    }

    public int count(int itemId) {
        int count = 0;

        for (StorageItem item : items) {
            if (item.itemId == itemId) {
                count += item.getQuantity();
            }
        }

        return count;
    }

    public boolean hasItem(int itemId) {
        for (StorageItem item : items) {
            if (item.itemId == itemId) {
                return true;
            }
        }

        return false;
    }

    public void addStackableItem(StorageItem itemToAdd) {
        for (StorageItem item : items) {
            if (item.itemId == itemToAdd.itemId) {
                item.increaseQuantity(itemToAdd.getQuantity());
                return;
            }
        }
    }

    public void addNonStackableItem(StorageItem itemToAdd) {
        for (int i = 0; i < itemToAdd.getQuantity(); i++) {
            items.add(new StorageItem(itemToAdd.itemId, 1));
        }
    }

    @Override
    public String toString() {
        String string = "ITEM CONTAINER CHANGED: | item container id: " + itemContainerId + "\r\n";

        for (StorageItem item : items) {
            string += item.itemId + ", quantity: " + item.getQuantity() + "\r\n";
        }

        return string;
    }
}