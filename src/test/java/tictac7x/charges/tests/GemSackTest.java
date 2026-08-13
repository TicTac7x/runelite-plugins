package tictac7x.charges.tests;

import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.gameval.ItemID;
import org.junit.*;
import tictac7x.charges.events.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.items.utils.*;

import java.util.*;

import static org.junit.Assert.*;

public class GemSackTest extends BaseTest {
    @Test
    public void GemSack() {
        U_GemSack gemSack = new U_GemSack(provider);
        setupInventoryItem(gemSack);

        // Golem crafting
        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "As you complete the golem it leaves a gift in your gem sack for you: 1 x Uncut sapphire."));
        assertEquals(1, gemSack.storage.getStorage().count(ItemID.UNCUT_SAPPHIRE));
    }
}