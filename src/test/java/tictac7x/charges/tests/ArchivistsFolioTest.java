package tictac7x.charges.tests;

import net.runelite.api.*;
import org.junit.*;
import tictac7x.charges.events.*;
import tictac7x.charges.items.utils.U_ArchivistsFolio;

import static org.junit.Assert.*;

public class ArchivistsFolioTest extends BaseTest {
    @Test
    public void ArchivistsFolio() {
        U_ArchivistsFolio folio = new U_ArchivistsFolio(provider);
        setupInventoryItem(folio);

        folio.setCharges(37);
        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "You open the folio and read the words. As you do, they glow and seem to spill out of the page around you."));
        assertEquals(36, folio.getTotalCharges());
    }
}
