package tictac7x.charges.tests;

import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import org.junit.*;
import tictac7x.charges.*;
import tictac7x.charges.events.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.items.helms.H_KandarinHeadgear;
import tictac7x.charges.items.utils.*;
import tictac7x.charges.items.weapons.W_ScytheOfVitur;
import tictac7x.charges.store.enums.*;
import tictac7x.charges.store.ids.ChargeId;

import java.awt.*;
import java.util.*;

import static org.junit.Assert.*;

public class ScytheOfViturTest extends BaseTest {
    @Test
    public void ScytheOfVitur() {
        W_ScytheOfVitur scytheOfVitur = new W_ScytheOfVitur(provider);
        setupEquipmentItem(scytheOfVitur);

        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "Your Sanguine Scythe of vitur has 100 charges remaining."));
        assertEquals(100, scytheOfVitur.getTotalCharges());

        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "Your sanguine scythe of Vitur has 200 charges remaining."));
        assertEquals(200, scytheOfVitur.getTotalCharges());
    }
}