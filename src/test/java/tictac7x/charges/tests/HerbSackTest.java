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
}