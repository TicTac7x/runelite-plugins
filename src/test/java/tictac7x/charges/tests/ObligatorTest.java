package tictac7x.charges.tests;

import net.runelite.api.*;
import org.junit.*;
import tictac7x.charges.events.*;
import tictac7x.charges.items.weapons.W_Obligator;

import static org.junit.Assert.*;

public class ObligatorTest extends BaseTest {
    @Test
    public void Obligator() {
        W_Obligator obligator = new W_Obligator(provider);
        setupEquipmentItem(obligator);

        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "Your obligator has 5,000 charges remaining."));
        assertEquals(5000, obligator.getTotalCharges());

        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "Your Obligator has 1 charge remaining."));
        assertEquals(1, obligator.getTotalCharges());
    }
}
