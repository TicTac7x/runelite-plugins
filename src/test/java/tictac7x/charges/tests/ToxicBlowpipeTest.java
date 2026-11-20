package tictac7x.charges.tests;

import net.runelite.api.ChatMessageType;
import net.runelite.api.ItemComposition;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import org.junit.*;
import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
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

    private void useOnBlowpipe(int itemId, String name) {
        store.onMenuOptionClicked(new CustomMenuOptionClicked(0, name, "Use", 0, "WIDGET_TARGET", itemId, -1));
        store.onMenuOptionClicked(new CustomMenuOptionClicked(0, name + " -> Toxic blowpipe", "Use", 0, "WIDGET_TARGET_ON_WIDGET", ItemID.TOXIC_BLOWPIPE_LOADED, -1));
    }

    private void inventory(StorageItem... items) {
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.INV, List.of(items)));
    }

    @Test
    public void loadingIsSyncedFromTheGameMessageOnly() {
        mockName(ItemID.SNAKEBOSS_SCALE, "Zulrah's scales");
        mockName(ItemID.AMETHYST_DART, "Amethyst dart");
        mockName(ItemID.TOXIC_BLOWPIPE_LOADED, "Toxic blowpipe");

        W_ToxicBlowpipe blowpipe = new W_ToxicBlowpipe(provider);
        store.setChargedItems(new ChargedItemBase[]{blowpipe});

        // Empty blowpipe, 8 darts and 10 scales in the inventory.
        inventory(
            new StorageItem(ItemID.TOXIC_BLOWPIPE_LOADED, 1),
            new StorageItem(ItemID.AMETHYST_DART, 8),
            new StorageItem(ItemID.SNAKEBOSS_SCALE, 10)
        );
        assertEquals("0", blowpipe.getTotalChargesString());

        // Load darts: the game prints the contents, then the darts leave the inventory.
        useOnBlowpipe(ItemID.AMETHYST_DART, "Amethyst dart");
        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "Darts: Amethyst dart x 8. Scales: 0 (0)."));
        inventory(
            new StorageItem(ItemID.TOXIC_BLOWPIPE_LOADED, 1),
            new StorageItem(ItemID.SNAKEBOSS_SCALE, 10)
        );
        assertEquals(8, blowpipe.getStorage().count(ItemID.AMETHYST_DART));
        assertEquals(0, blowpipe.getStorage().count(ItemID.SNAKEBOSS_SCALE));
        assertEquals("0", blowpipe.getTotalChargesString());

        // Load scales the same way. Must end at 10, not 20.
        useOnBlowpipe(ItemID.SNAKEBOSS_SCALE, "Zulrah's scales");
        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "Darts: Amethyst dart x 8. Scales: 10 (1)."));
        inventory(
            new StorageItem(ItemID.TOXIC_BLOWPIPE_LOADED, 1)
        );
        assertEquals(8, blowpipe.getStorage().count(ItemID.AMETHYST_DART));
        assertEquals(10, blowpipe.getStorage().count(ItemID.SNAKEBOSS_SCALE));
        assertEquals("8", blowpipe.getTotalChargesString());
    }

    @Test
    public void chargesAreTheScarcerOfDartsAndScales() {
        W_ToxicBlowpipe blowpipe = new W_ToxicBlowpipe(provider);
        store.setChargedItems(new ChargedItemBase[]{blowpipe});
        inventory(new StorageItem(ItemID.TOXIC_BLOWPIPE_LOADED, 1));

        blowpipe.storage.put(ItemID.SNAKEBOSS_SCALE, 600);
        assertEquals("0", blowpipe.getTotalChargesString());
        assertEquals(config.getColorEmpty(), blowpipe.getTotalTextColor());

        blowpipe.storage.put(ItemID.DRAGON_DART, 50);
        assertEquals("50", blowpipe.getTotalChargesString());

        // Darts gone, scales left: must read 0, never fall back to the scale count.
        blowpipe.storage.remove(ItemID.DRAGON_DART);
        assertEquals("0", blowpipe.getTotalChargesString());
        assertEquals(config.getColorEmpty(), blowpipe.getTotalTextColor());
    }
}
