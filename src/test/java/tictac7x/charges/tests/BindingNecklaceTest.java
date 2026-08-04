package tictac7x.charges.tests;

import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.gameval.*;
import net.runelite.api.gameval.InventoryID;
import org.junit.*;
import tictac7x.charges.events.*;
import tictac7x.charges.items.capes.*;
import tictac7x.charges.items.jewelry.*;

import java.util.*;

import static org.junit.Assert.*;

public class BindingNecklaceTest extends BaseTest {
    @Test
    public void BindingNecklace() {
        J_BindingNecklace bindingNecklace = new J_BindingNecklace(provider);
        setupEquipmentItem(bindingNecklace);
        setupInventoryItem(bindingNecklace);

        // Check plural
        store.onChatMessage(
            new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "You have 10 charges left before your Binding necklace disintegrates.")
        );
        assertEquals(10, bindingNecklace.getTotalCharges());
        assertEquals(config.getColorActivated(), bindingNecklace.getTotalTextColor());

        // Check singular
        store.onChatMessage(
            new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "You have one charge left before your Binding necklace disintegrates.")
        );
        assertEquals(1, bindingNecklace.getTotalCharges());
        assertEquals(config.getColorActivated(), bindingNecklace.getTotalTextColor());

        // Destroyed
        store.onChatMessage(
            new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "Your Binding necklace has disintegrated.")
        );
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.WORN, new ArrayList<>()));
        store.onGameTick(new GameTick());
        assertEquals(16, bindingNecklace.getTotalCharges());
        assertEquals(config.getColorEmpty(), bindingNecklace.getTotalTextColor());

        // Not equipped
        store.onChatMessage(
            new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "You partially succeed to bind the temple's power into Mud rune.")
        );
        assertEquals(16, bindingNecklace.getTotalCharges());
        assertEquals(config.getColorEmpty(), bindingNecklace.getTotalTextColor());

        // Equipped
        setupEquipmentItem(bindingNecklace);
        store.onChatMessage(
            new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "You bind the temple's power into Mud rune.")
        );
        assertEquals(15, bindingNecklace.getTotalCharges());
        assertEquals(config.getColorActivated(), bindingNecklace.getTotalTextColor());

    }
}