package tictac7x.charges.tests;

import net.runelite.api.ItemComposition;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import org.junit.*;
import tictac7x.charges.events.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.items.weapons.blowpipes.*;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ToxicBlowpipeTest extends BaseTest {
    private void mockName(int itemId, String name) {
        ItemComposition composition = mock(ItemComposition.class);
        lenient().when(composition.getName()).thenReturn(name);
        lenient().when(itemManager.getItemComposition(itemId)).thenReturn(composition);
    }

    private void useScalesOnBlowpipe() {
        store.onMenuOptionClicked(new CustomMenuOptionClicked(0, "Zulrah's scales", "Use", 0, "WIDGET_TARGET", ItemID.SNAKEBOSS_SCALE, -1));
        store.onMenuOptionClicked(new CustomMenuOptionClicked(0, "Zulrah's scales -> Toxic blowpipe", "Use", 0, "WIDGET_TARGET_ON_WIDGET", ItemID.TOXIC_BLOWPIPE_LOADED, -1));
    }

    @Test
    public void loadingScalesIsCountedOnce() {
        mockName(ItemID.SNAKEBOSS_SCALE, "Zulrah's scales");
        mockName(ItemID.TOXIC_BLOWPIPE_LOADED, "Toxic blowpipe");

        W_ToxicBlowpipe blowpipe = new W_ToxicBlowpipe(provider);
        store.setChargedItems(new tictac7x.charges.item.ChargedItemBase[]{blowpipe});

        // Blowpipe with 8 darts and no scales, 10 scales in the inventory.
        blowpipe.storage.put(ItemID.AMETHYST_DART, 8);
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.INV, List.of(
            new StorageItem(ItemID.TOXIC_BLOWPIPE_LOADED, 1),
            new StorageItem(ItemID.SNAKEBOSS_SCALE, 10)
        )));
        assertEquals(0, blowpipe.getStorage().count(ItemID.SNAKEBOSS_SCALE));

        // Use the scales on the blowpipe, scales leave the inventory.
        useScalesOnBlowpipe();
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.INV, List.of(
            new StorageItem(ItemID.TOXIC_BLOWPIPE_LOADED, 1)
        )));
        assertEquals(10, blowpipe.getStorage().count(ItemID.SNAKEBOSS_SCALE));
        assertEquals(8, blowpipe.getStorage().count(ItemID.AMETHYST_DART));
        assertEquals("8", blowpipe.getTotalChargesString());

        // The client often fires the same container state again. Must not double count.
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.INV, List.of(
            new StorageItem(ItemID.TOXIC_BLOWPIPE_LOADED, 1)
        )));
        assertEquals(10, blowpipe.getStorage().count(ItemID.SNAKEBOSS_SCALE));

        // Equipment update right after must not count either.
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.WORN, List.of()));
        assertEquals(10, blowpipe.getStorage().count(ItemID.SNAKEBOSS_SCALE));
    }
}
