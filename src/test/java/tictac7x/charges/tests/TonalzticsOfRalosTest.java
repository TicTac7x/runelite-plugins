package tictac7x.charges.tests;

import net.runelite.api.ChatMessageType;
import net.runelite.api.Player;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.gameval.AnimationID;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import org.junit.*;
import tictac7x.charges.events.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.items.weapons.*;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TonalzticsOfRalosTest extends BaseTest {
    @Test
    public void TonalzticsOfRalos() {
        W_TonalzticsOfRalos tonalztics = new W_TonalzticsOfRalos(provider);
        setupEquipmentItem(tonalztics);

        // Charge.
        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "You apply 50 charges to your Tonalztics of Ralos."));
        assertEquals(50, tonalztics.storage.getStorage().count(ItemID.SUNFIRESPLINTER));
        assertEquals(50, tonalztics.getCharges(ItemID.TONALZTICS_OF_RALOS_CHARGED));

        // Add charges to an already charged weapon, the message holds the total amount of charges.
        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "You apply an additional 1,500 charges to your Tonalztics of Ralos. It now has 1,550 charges in total."));
        assertEquals(1550, tonalztics.storage.getStorage().count(ItemID.SUNFIRESPLINTER));
        assertEquals(1550, tonalztics.getCharges(ItemID.TONALZTICS_OF_RALOS_CHARGED));

        // Check.
        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "Your Tonalztics of Ralos has 1,540 charges remaining."));
        assertEquals(1540, tonalztics.storage.getStorage().count(ItemID.SUNFIRESPLINTER));

        // Attack (regular throw).
        attack(AnimationID.HUMAN_GLAIVE_RALOS01_CHARGED_THROW);
        assertEquals(1539, tonalztics.storage.getStorage().count(ItemID.SUNFIRESPLINTER));

        // Attack (special attack).
        attack(AnimationID.HUMAN_GLAIVE_RALOS01_CHARGED_SPECIAL);
        assertEquals(1538, tonalztics.storage.getStorage().count(ItemID.SUNFIRESPLINTER));

        // The uncharged throw and the uncharged special attack do not use any charges.
        attack(AnimationID.HUMAN_GLAIVE_RALOS01_UNCHARGED_THROW);
        attack(AnimationID.HUMAN_GLAIVE_RALOS01_UNCHARGED_SPECIAL);
        assertEquals(1538, tonalztics.storage.getStorage().count(ItemID.SUNFIRESPLINTER));

        // Uncharge.
        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "You uncharge your Tonalztics of Ralos, regaining 1538 sunfire splinters in the process."));
        assertEquals(0, tonalztics.storage.getStorage().count(ItemID.SUNFIRESPLINTER));

        // Bank charge.
        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "The banker charges your Tonalztics of Ralos using 14x Sunfire splinters."));
        assertEquals(14, tonalztics.storage.getStorage().count(ItemID.SUNFIRESPLINTER));

        // Bank charge with a comma separated amount.
        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "The banker charges your Tonalztics of Ralos using 1,000x Sunfire splinters."));
        assertEquals(1014, tonalztics.storage.getStorage().count(ItemID.SUNFIRESPLINTER));
    }

    @Test
    public void UnchargedTonalzticsOfRalos() {
        W_TonalzticsOfRalos tonalztics = new W_TonalzticsOfRalos(provider);
        setupInventoryItem(tonalztics);

        // The item is the uncharged variant.
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.INV, List.of(new StorageItem(ItemID.TONALZTICS_OF_RALOS_UNCHARGED, 1))));

        assertEquals(0, tonalztics.getCharges(ItemID.TONALZTICS_OF_RALOS_UNCHARGED));
    }

    private void attack(int animationId) {
        Player player = mock(Player.class);
        when(player.getAnimation()).thenReturn(animationId);
        when(client.getLocalPlayer()).thenReturn(player);

        AnimationChanged animationChanged = new AnimationChanged();
        animationChanged.setActor(player);

        store.onAnimationChanged(animationChanged);
    }
}
