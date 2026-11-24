package tictac7x.charges.tests;

import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import org.junit.*;
import tictac7x.charges.*;
import tictac7x.charges.events.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.items.utils.*;
import tictac7x.charges.store.enums.*;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HerbSackTest extends BaseTest {
    @Test
    public void FurPouch() {
        U_HerbSack herbSack = new U_HerbSack(provider);
        setupInventoryItem(herbSack);

        herbSack.storage.put(ItemID.UNIDENTIFIED_AVANTOE, 30);
        herbSack.storage.put(ItemID.UNIDENTIFIED_GUAM, 20);

        // Total quantities
        when(configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.herb_sack + TicTac7xChargesImprovedConfig._display)).thenReturn(StorageDisplay.TOTAL);
        assertEquals(50, herbSack.getTotalCharges());
        assertEquals(config.getColorDefault(), herbSack.getTotalTextColor());

        // Individual quantities
        when(configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.herb_sack + TicTac7xChargesImprovedConfig._display)).thenReturn(StorageDisplay.INDIVIDUAL);
        assertEquals(30, herbSack.getTotalCharges());
        assertEquals(config.getColorEmpty(), herbSack.getTotalTextColor());
    }

    @Test
    public void EmptyThenCleanHerbs() {
        U_HerbSack herbSack = new U_HerbSack(provider);
        setupInventoryItem(herbSack);
        herbSack.storage.put(ItemID.UNIDENTIFIED_RANARR, 3);

        // Empty to inventory.
        store.onGameTick(new GameTick());
        store.onGameTick(new GameTick());
        store.onMenuOptionClicked(new CustomMenuOptionClicked(
            -1, "Herb sack", "Empty", -1, "", ItemID.SLAYER_HERB_SACK, -1
        ));
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.INV, List.of(
            new StorageItem(ItemID.SLAYER_HERB_SACK, 1),
            new StorageItem(ItemID.UNIDENTIFIED_RANARR, 3)
        )));
        assertEquals(0, herbSack.getStorage().count(ItemID.UNIDENTIFIED_RANARR));

        // Clean a herb on the next tick.
        store.onGameTick(new GameTick());
        store.onMenuOptionClicked(new CustomMenuOptionClicked(
            -1, "Grimy ranarr weed", "Clean", -1, "", ItemID.UNIDENTIFIED_RANARR, -1
        ));
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.INV, List.of(
            new StorageItem(ItemID.SLAYER_HERB_SACK, 1),
            new StorageItem(ItemID.UNIDENTIFIED_RANARR, 2),
            new StorageItem(ItemID.RANARR_WEED, 1)
        )));
        assertEquals(0, herbSack.getStorage().count(ItemID.UNIDENTIFIED_RANARR));
    }

    @Test
    public void EmptyPartiallyToInventory() {
        U_HerbSack herbSack = new U_HerbSack(provider);
        setupInventoryItem(herbSack);
        herbSack.storage.put(ItemID.UNIDENTIFIED_RANARR, 30);

        store.onMenuOptionClicked(new CustomMenuOptionClicked(
            -1, "Herb sack", "Empty", -1, "", ItemID.SLAYER_HERB_SACK, -1
        ));
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.INV, List.of(
            new StorageItem(ItemID.SLAYER_HERB_SACK, 1),
            new StorageItem(ItemID.UNIDENTIFIED_RANARR, 27)
        )));
        assertEquals(3, herbSack.getStorage().count(ItemID.UNIDENTIFIED_RANARR));
    }

    @Test
    public void EmptyToInventoryIsSaved() {
        U_HerbSack herbSack = new U_HerbSack(provider);
        setupInventoryItem(herbSack);
        herbSack.storage.put(ItemID.UNIDENTIFIED_RANARR, 3);
        clearInvocations(configManager);

        store.onMenuOptionClicked(new CustomMenuOptionClicked(
            -1, "Herb sack", "Empty", -1, "", ItemID.SLAYER_HERB_SACK, -1
        ));
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.INV, List.of(
            new StorageItem(ItemID.SLAYER_HERB_SACK, 1),
            new StorageItem(ItemID.UNIDENTIFIED_RANARR, 3)
        )));
        verify(configManager).setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.herb_sack + "_storage", "[]");
    }

    @Test
    public void EmptyToBankIsSaved() {
        U_HerbSack herbSack = new U_HerbSack(provider);
        setupInventoryItem(herbSack);
        herbSack.storage.put(ItemID.UNIDENTIFIED_RANARR, 3);
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.BANK, List.of(
            new StorageItem(ItemID.UNIDENTIFIED_RANARR, 10)
        )));
        clearInvocations(configManager);

        store.onMenuOptionClicked(new CustomMenuOptionClicked(
            -1, "Herb sack", TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank, -1, "", ItemID.SLAYER_HERB_SACK, -1
        ));
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.BANK, List.of(
            new StorageItem(ItemID.UNIDENTIFIED_RANARR, 13)
        )));
        assertEquals(0, herbSack.getStorage().count(ItemID.UNIDENTIFIED_RANARR));
        verify(configManager).setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.herb_sack + "_storage", "[]");
    }
}