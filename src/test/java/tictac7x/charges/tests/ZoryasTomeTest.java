package tictac7x.charges.tests;

import net.runelite.api.*;
import org.junit.*;
import tictac7x.charges.events.*;
import tictac7x.charges.items.weapons.W_ZoryasTome;

import static org.junit.Assert.*;

public class ZoryasTomeTest extends BaseTest {
    @Test
    public void ZoryasTome() {
        W_ZoryasTome tome = new W_ZoryasTome(provider);
        setupEquipmentItem(tome);

        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "Zorya's tome has 34 charges remaining."));
        assertEquals(34, tome.getTotalCharges());

        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "Zorya's tome has 1,234 charges remaining."));
        assertEquals(1234, tome.getTotalCharges());
    }
}
