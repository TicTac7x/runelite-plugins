package tictac7x.charges.items.gemcontainers;

import org.junit.Test;
import tictac7x.charges.store.ids.ItemId;

import static org.junit.Assert.assertEquals;

public class ThieveStallTest extends GemContainerTestBase {

    @Test
    public void testStallTheftSapphireUpdatesGemBag() {
        fire("You steal an uncut sapphire and add it to your gem bag.", gemBag, gemPouch);
        assertEquals(1, count(gemBag, ItemId.UNCUT_SAPPHIRE));
    }

    @Test
    public void testStallTheftSapphireDoesNotUpdateGemPouch() {
        fire("You steal an uncut sapphire and add it to your gem bag.", gemBag, gemPouch);
        assertEquals(0, count(gemPouch, ItemId.UNCUT_SAPPHIRE));
    }

    @Test
    public void testStallTheftOpalUpdatesGemPouch() {
        fire("You steal an uncut opal and add it to your gem pouch.", gemBag, gemPouch);
        assertEquals(1, count(gemPouch, ItemId.UNCUT_OPAL));
    }

    @Test
    public void testStallTheftOpalDoesNotUpdateGemBag() {
        fire("You steal an uncut opal and add it to your gem pouch.", gemBag, gemPouch);
        assertEquals(0, count(gemBag, ItemId.UNCUT_OPAL));
    }
}
