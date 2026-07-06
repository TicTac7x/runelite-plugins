package tictac7x.charges.items.gemcontainers;

import org.junit.Ignore;
import org.junit.Test;
import tictac7x.charges.store.ids.ItemId;

import static org.junit.Assert.assertEquals;

public class EmptyToInventoryTest extends GemContainerTestBase {

    @Test
    public void testEmptyToInventoryWithGemsGemBag() {
        gemBag.storage.put(ItemId.UNCUT_SAPPHIRE, 10);
        fire("The gem bag is now empty", gemBag, gemPouch);
        assertEquals(0, count(gemBag, ItemId.UNCUT_SAPPHIRE));
    }

    @Test
    public void testEmptyToInventoryAlreadyEmptyGemBag() {
        gemBag.storage.put(ItemId.UNCUT_SAPPHIRE, 10);
        fire("The gem bag is empty", gemBag, gemPouch);
        assertEquals(0, count(gemBag, ItemId.UNCUT_SAPPHIRE));
    }

    @Test
    public void testEmptyToInventoryWithGemsGemPouch() {
        gemPouch.storage.put(ItemId.UNCUT_OPAL, 5);
        fire("The gem pouch is now empty", gemBag, gemPouch);
        assertEquals(0, count(gemPouch, ItemId.UNCUT_OPAL));
    }

    @Test
    public void testEmptyToInventoryAlreadyEmptyGemPouch() {
        gemPouch.storage.put(ItemId.UNCUT_OPAL, 5);
        fire("The gem pouch is empty", gemBag, gemPouch);
        assertEquals(0, count(gemPouch, ItemId.UNCUT_OPAL));
    }

    @Test
    public void testEmptyToFullInventoryUpdatesGemBag() {
        fire("Left in bag: Opal: 50 / Jade: 43 / Red Topaz: 60 Sapphires: 12 / Emeralds: 4 / Rubies: 0 Diamonds: 0 / Dragonstones: 0", gemBag);
        assertEquals(12, count(gemBag, ItemId.UNCUT_SAPPHIRE));
        assertEquals(4,  count(gemBag, ItemId.UNCUT_EMERALD));
        assertEquals(0,  count(gemBag, ItemId.UNCUT_RUBY));
        assertEquals(0,  count(gemBag, ItemId.UNCUT_DIAMOND));
        assertEquals(0,  count(gemBag, ItemId.UNCUT_DRAGONSTONE));
    }

    @Test
    public void testEmptyToFullInventoryUpdatesGemPouch() {
        fire("Left in bag: Opal: 50 / Jade: 43 / Red Topaz: 60 Sapphires: 12 / Emeralds: 4 / Rubies: 0 Diamonds: 0 / Dragonstones: 0", gemPouch);
        assertEquals(50, count(gemPouch, ItemId.UNCUT_OPAL));
        assertEquals(43, count(gemPouch, ItemId.UNCUT_JADE));
        assertEquals(60, count(gemPouch, ItemId.UNCUT_RED_TOPAZ));
    }

    // The game always sends "The gem bag is now empty" regardless of which container was emptied,
    // so the gem pouch storage is incorrectly cleared when the gem bag message is received.
    @Ignore("Known game limitation: empty-to-inventory message always says 'gem bag'")
    @Test
    public void testEmptyToInventoryGemBagMessageDoesNotClearGemPouch() {
        gemBag.storage.put(ItemId.UNCUT_SAPPHIRE, 10);
        gemPouch.storage.put(ItemId.UNCUT_OPAL, 5);
        fire("The gem bag is now empty", gemBag, gemPouch);
        assertEquals(0, count(gemBag, ItemId.UNCUT_SAPPHIRE));
        assertEquals(5, count(gemPouch, ItemId.UNCUT_OPAL));
    }
}
