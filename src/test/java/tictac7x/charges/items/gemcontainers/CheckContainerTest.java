package tictac7x.charges.items.gemcontainers;

import org.junit.Test;
import tictac7x.charges.store.ids.ItemId;

import static org.junit.Assert.assertEquals;

public class CheckContainerTest extends GemContainerTestBase {

    @Test
    public void testCheckGemBagUpdatesPreciousGems() {
        fire("Sapphires: 10 / Emeralds: 5 / Rubies: 3 / Diamonds: 2 / Dragonstones: 1", gemBag, gemPouch);
        assertEquals(10, count(gemBag, ItemId.UNCUT_SAPPHIRE));
        assertEquals(5,  count(gemBag, ItemId.UNCUT_EMERALD));
        assertEquals(3,  count(gemBag, ItemId.UNCUT_RUBY));
        assertEquals(2,  count(gemBag, ItemId.UNCUT_DIAMOND));
        assertEquals(1,  count(gemBag, ItemId.UNCUT_DRAGONSTONE));
    }

    @Test
    public void testCheckGemBagDoesNotUpdateGemPouch() {
        fire("Sapphires: 10 / Emeralds: 5 / Rubies: 3 / Diamonds: 2 / Dragonstones: 1", gemBag, gemPouch);
        assertEquals(0, count(gemPouch, ItemId.UNCUT_OPAL));
        assertEquals(0, count(gemPouch, ItemId.UNCUT_JADE));
        assertEquals(0, count(gemPouch, ItemId.UNCUT_RED_TOPAZ));
    }

    @Test
    public void testCheckGemPouchUpdatesSemiPreciousGems() {
        fire("Opal: 5 / Jade: 3 / Red Topaz: 2", gemBag, gemPouch);
        assertEquals(5, count(gemPouch, ItemId.UNCUT_OPAL));
        assertEquals(3, count(gemPouch, ItemId.UNCUT_JADE));
        assertEquals(2, count(gemPouch, ItemId.UNCUT_RED_TOPAZ));
    }

    @Test
    public void testCheckGemPouchDoesNotUpdateGemBag() {
        fire("Opal: 5 / Jade: 3 / Red Topaz: 2", gemBag, gemPouch);
        assertEquals(0, count(gemBag, ItemId.UNCUT_SAPPHIRE));
        assertEquals(0, count(gemBag, ItemId.UNCUT_EMERALD));
    }

    @Test
    public void testCheckGemSackUpdatesAllGems() {
        fire("Opal: 50 / Jade: 43 / Red Topaz: 60 Sapphires: 12 / Emeralds: 4 / Rubies: 0 Diamonds: 0 / Dragonstones: 0", gemSack);
        assertEquals(50, count(gemSack, ItemId.UNCUT_OPAL));
        assertEquals(43, count(gemSack, ItemId.UNCUT_JADE));
        assertEquals(60, count(gemSack, ItemId.UNCUT_RED_TOPAZ));
        assertEquals(12, count(gemSack, ItemId.UNCUT_SAPPHIRE));
        assertEquals(4,  count(gemSack, ItemId.UNCUT_EMERALD));
        assertEquals(0,  count(gemSack, ItemId.UNCUT_RUBY));
        assertEquals(0,  count(gemSack, ItemId.UNCUT_DIAMOND));
        assertEquals(0,  count(gemSack, ItemId.UNCUT_DRAGONSTONE));
    }
}
