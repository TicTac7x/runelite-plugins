package tictac7x.charges.items.gemcontainers;

import org.junit.Test;
import tictac7x.charges.store.ids.ItemId;

import static org.junit.Assert.assertEquals;

public class PickpocketTest extends GemContainerTestBase {

    @Test
    public void testPickpocketSapphireUpdatesGemBag() {
        fire("You put the stolen Uncut Sapphire into your gem bag.", gemBag, gemPouch);
        assertEquals(1, count(gemBag, ItemId.UNCUT_SAPPHIRE));
    }

    @Test
    public void testPickpocketSapphireDoesNotUpdateGemPouch() {
        fire("You put the stolen Uncut Sapphire into your gem bag.", gemBag, gemPouch);
        assertEquals(0, count(gemPouch, ItemId.UNCUT_SAPPHIRE));
    }

    @Test
    public void testPickpocketOpalUpdatesGemPouch() {
        fire("You put the stolen Uncut Opal into your gem pouch.", gemBag, gemPouch);
        assertEquals(1, count(gemPouch, ItemId.UNCUT_OPAL));
    }

    @Test
    public void testPickpocketOpalDoesNotUpdateGemBag() {
        fire("You put the stolen Uncut Opal into your gem pouch.", gemBag, gemPouch);
        assertEquals(0, count(gemBag, ItemId.UNCUT_OPAL));
    }

    @Test
    public void testPickpocketSapphireUpdatesGemSack() {
        fire("You put the stolen Uncut Sapphire into your gem sack.", gemSack);
        assertEquals(1, count(gemSack, ItemId.UNCUT_SAPPHIRE));
    }

    @Test
    public void testPickpocketOpalUpdatesGemSack() {
        fire("You put the stolen Uncut Opal into your gem sack.", gemSack);
        assertEquals(1, count(gemSack, ItemId.UNCUT_OPAL));
    }

}
