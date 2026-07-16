package tictac7x.charges;

import net.runelite.api.*;
import net.runelite.api.gameval.ItemID;
import org.junit.*;
import tictac7x.charges.events.*;
import tictac7x.charges.items.weapons.venator.*;
import tictac7x.charges.store.*;

import java.util.*;

import static org.junit.Assert.*;

public class VenatorBowTest {
    private Map<String, String> settings;
    private MyClient myClient;
    private MyItemManager itemManager;
    private MyConfigManager configManager;
    private Store store;
    private Provider provider;

    @Before
    public void setup() {
        settings = new HashMap<>();
        myClient = new MyClient(
            (varbitId) -> 0,
            () -> 0,
            () -> GameState.LOGGED_IN,
            (actor -> true)
        );
        itemManager = new MyItemManager((itemId) -> new MyItemComposition(0, "", true, false));
        configManager = new MyConfigManager((key, value) -> {
            settings.put(key, value);
        });

        store = new Store(myClient, itemManager, configManager);
    }

    @Test
    public void VenatorBow() {
        W_VenatorBow venatorBow = new W_VenatorBow(provider);

        store.onItemContainerChanged();

        CustomChatMessage message = new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "Venator bow is already fully charged.");
        store.onChatMessage(message);

        assertEquals(venatorBow.storage.getItem(ItemID.ANCIENT_ESSENCE).get().getQuantity(), 50000);
    }
}
