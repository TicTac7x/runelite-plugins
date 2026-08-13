package tictac7x.charges.tests;

import net.runelite.api.*;
import net.runelite.api.gameval.ItemID;
import org.junit.*;
import tictac7x.charges.events.*;
import tictac7x.charges.items.utils.*;

import static org.junit.Assert.*;

public class SeedBoxTest extends BaseTest {
    @Test
    public void SeedBox() {
        U_SeedBox seedBox = new U_SeedBox(provider);
        setupInventoryItem(seedBox);

        seedBox.storage.put(ItemID.WATERMELON_SEED, 100);
        seedBox.storage.put(ItemID.RANARR_SEED, 10);

        // Planting multiple seeds directly from seed box
        assertEquals(100, seedBox.storage.getStorage().count(ItemID.WATERMELON_SEED));
        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "3 watermelon seeds were taken from your seed box."));
        assertEquals(97, seedBox.storage.getStorage().count(ItemID.WATERMELON_SEED));

        // Planting single seed directly from seed box
        assertEquals(10, seedBox.storage.getStorage().count(ItemID.RANARR_SEED));
        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "A ranarr seed was taken from your seed box."));
        assertEquals(9, seedBox.storage.getStorage().count(ItemID.RANARR_SEED));
    }
}