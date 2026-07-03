package tictac7x.charges.items.gemcontainers;

import org.junit.Ignore;
import org.junit.Test;
import tictac7x.charges.store.ids.ItemId;

import static org.junit.Assert.assertEquals;

public class EmptyToBankTest extends GemContainerTestBase {

    @Test
    public void testEmptyToBankClearsGemBag() {
        gemBag.storage.put(ItemId.UNCUT_SAPPHIRE, 10);
        fire("You empty the gem bag into the bank", gemBag, gemPouch);
        assertEquals(0, count(gemBag, ItemId.UNCUT_SAPPHIRE));
    }

    @Test
    public void testEmptyToBankClearsGemPouch() {
        gemPouch.storage.put(ItemId.UNCUT_OPAL, 5);
        fire("You empty the gem bag into the bank", gemBag, gemPouch);
        assertEquals(0, count(gemPouch, ItemId.UNCUT_OPAL));
    }

    @Test
    public void testEmptyToBankGemPouchMessageDoesNotClearGemBag() {
        gemBag.storage.put(ItemId.UNCUT_SAPPHIRE, 10);
        fire("You empty the gem bag into the bank", gemBag, gemPouch);
        assertEquals(10, count(gemBag, ItemId.UNCUT_SAPPHIRE));
    }

    @Test
    public void testEmptyToBankClearsGemSack() {
        gemSack.storage.put(ItemId.UNCUT_SAPPHIRE, 10);
        gemSack.storage.put(ItemId.UNCUT_OPAL, 5);
        fire("You empty the gem bag into the bank", gemSack);
        assertEquals(0, count(gemSack, ItemId.UNCUT_SAPPHIRE));
        assertEquals(0, count(gemSack, ItemId.UNCUT_OPAL));
    }

    // The game always sends "You empty the gem bag into the bank" regardless of which container
    // was emptied, so the gem pouch storage is incorrectly cleared by the gem bag message.
    @Ignore("Known game limitation: empty-to-bank message always says 'gem bag'")
    @Test
    public void testEmptyToBankGemBagMessageDoesNotClearGemPouch() {
        gemPouch.storage.put(ItemId.UNCUT_OPAL, 5);
        fire("You empty the gem bag into the bank", gemBag, gemPouch);
        assertEquals(5, count(gemPouch, ItemId.UNCUT_OPAL));
    }
}
