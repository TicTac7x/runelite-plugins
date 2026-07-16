package tictac7x.charges;

import net.runelite.api.*;
import net.runelite.api.gameval.ItemID;
import org.junit.*;
import tictac7x.charges.events.*;
import tictac7x.charges.items.weapons.venator.*;

import java.util.List;

import static org.junit.Assert.*;

public class VenatorBows extends BaseTest {
    @Test
    public void VenatorBow() {
        W_VenatorBow venatorBow = new W_VenatorBow(provider);
        setupEquipmentItem(venatorBow);

        // Check when full
        store.onMenuOptionClicked(new CustomMenuOptionClicked(-1, "Venator bow", "Check", -1, "", ItemID.VENATOR_BOW, -1));
        CustomChatMessage messageCheckFull = new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "Venator bow is already fully charged.");
        store.onChatMessage(messageCheckFull);
        assertEquals(50000, venatorBow.storage.getStorage().count(ItemID.ANCIENT_ESSENCE));

        // Check
        store.onMenuOptionClicked(new CustomMenuOptionClicked(-1, "Venator bow", "Check", -1, "", ItemID.VENATOR_BOW, -1));
        CustomChatMessage messageCheck = new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "Your venator bow has 1000 charges remaining.");
        store.onChatMessage(messageCheck);
        assertEquals(1000, venatorBow.storage.getStorage().count(ItemID.ANCIENT_ESSENCE));

        // Uncharge
        CustomChatMessage messageUncharge = new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "You fully uncharge your venator bow, regaining 100 ancient essence in the process.");
        store.onChatMessage(messageUncharge);
        assertEquals(0, venatorBow.storage.getStorage().count(ItemID.ANCIENT_ESSENCE));

        // Charge
        store.onMenuOptionClicked(new CustomMenuOptionClicked(-1, "Ancient essence -> Venator bow", "Use", -1, "", -1, -1));
        CustomChatMessage messageCharge = new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "You use 100 ancient essence to charge your venator bow. It now has 45000 charges.");
        store.onChatMessage(messageCharge);
        assertEquals(45000, venatorBow.storage.getStorage().count(ItemID.ANCIENT_ESSENCE));

        // Attack
        store.onGraphicChanged(new CustomGraphicChanged("Player", List.of(2289)));
        assertEquals(44999, venatorBow.storage.getStorage().count(ItemID.ANCIENT_ESSENCE));
    }

    @Test
    public void EchoVenatorBow() {
        W_EchoVenatorBow echoVenatorBow = new W_EchoVenatorBow(provider);
        setupEquipmentItem(echoVenatorBow);

        // Check when full
        store.onMenuOptionClicked(new CustomMenuOptionClicked(-1, "Echo venator bow", "Check", -1, "", ItemID.VENATOR_BOW_ORNAMENT, -1));
        CustomChatMessage messageCheckFull = new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "Echo venator bow is already fully charged.");
        store.onChatMessage(messageCheckFull);
        assertEquals(50000, echoVenatorBow.storage.getStorage().count(ItemID.ANCIENT_ESSENCE));

        // Check
        store.onMenuOptionClicked(new CustomMenuOptionClicked(-1, "Echo venator bow", "Check", -1, "", ItemID.VENATOR_BOW_ORNAMENT, -1));
        CustomChatMessage messageCheck = new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "Your echo venator bow has 1000 charges remaining.");
        store.onChatMessage(messageCheck);
        assertEquals(1000, echoVenatorBow.storage.getStorage().count(ItemID.ANCIENT_ESSENCE));

        // Uncharge
        CustomChatMessage messageUncharge = new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "You fully uncharge your echo venator bow, regaining 100 ancient essence in the process.");
        store.onChatMessage(messageUncharge);
        assertEquals(0, echoVenatorBow.storage.getStorage().count(ItemID.ANCIENT_ESSENCE));

        // Charge
        store.onMenuOptionClicked(new CustomMenuOptionClicked(-1, "Ancient essence -> Echo venator bow", "Use", -1, "", -1, -1));
        CustomChatMessage messageCharge = new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "You use 100 ancient essence to charge your echo venator bow. It now has 45000 charges.");
        store.onChatMessage(messageCharge);
        assertEquals(45000, echoVenatorBow.storage.getStorage().count(ItemID.ANCIENT_ESSENCE));

        // Attack
        store.onGraphicChanged(new CustomGraphicChanged("Player", List.of(2289)));
        assertEquals(44999, echoVenatorBow.storage.getStorage().count(ItemID.ANCIENT_ESSENCE));
    }
}
