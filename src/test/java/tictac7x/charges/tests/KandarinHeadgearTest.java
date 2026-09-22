package tictac7x.charges.tests;

import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import org.junit.*;
import tictac7x.charges.*;
import tictac7x.charges.events.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.items.helms.H_KandarinHeadgear;
import tictac7x.charges.items.utils.*;
import tictac7x.charges.store.enums.*;
import tictac7x.charges.store.ids.ChargeId;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.Assert.*;

public class KandarinHeadgearTest extends BaseTest {
    @Test
    public void KandarinHeadgear() {
        H_KandarinHeadgear kandarinHeadgear = new H_KandarinHeadgear(provider);
        setupEquipmentItem(kandarinHeadgear);

        // Fill from inventory.
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.WORN, List.of(
            new StorageItem(ItemID.SEERS_HEADBAND_ELITE, 1)
        )));

        assertEquals(ChargeId.UNLIMITED, kandarinHeadgear.getTotalCharges());
        assertEquals(ChargeId.UNLIMITED, kandarinHeadgear.getCharges(kandarinHeadgear.itemId));
    }
}