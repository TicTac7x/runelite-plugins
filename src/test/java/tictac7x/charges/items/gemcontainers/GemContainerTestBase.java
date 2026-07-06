package tictac7x.charges.items.gemcontainers;

import com.google.gson.Gson;
import net.runelite.api.ChatMessageType;
import net.runelite.api.ItemComposition;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import org.junit.Before;
import tictac7x.charges.events.CustomChatMessage;
import tictac7x.charges.item.listeners.ListenerOnChatMessage;
import tictac7x.charges.item.storage.StorageItems;
import tictac7x.charges.items.utils.*;
import tictac7x.charges.store.Provider;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

abstract class GemContainerTestBase {

    ListenerOnChatMessage listener;

    U_GemBag gemBag;
    U_GemPouch gemPouch;
    U_GemSatchel gemSatchel;
    U_GemTote gemTote;
    U_GemSack gemSack;

    @Before
    public void setUp() {
        final ItemManager itemManager = mock(ItemManager.class);
        final ItemComposition itemComposition = mock(ItemComposition.class);
        when(itemComposition.getName()).thenReturn("");
        when(itemManager.getItemComposition(anyInt())).thenReturn(itemComposition);

        final Provider provider = new Provider(
                null,
                mock(ClientThread.class),
                null,
                mock(ConfigManager.class),
                itemManager, null, null, null, null, null, null,
                null,
                new Gson()
        );

        listener = new ListenerOnChatMessage(provider);

        gemBag = new U_GemBag(provider);
        gemPouch = new U_GemPouch(provider);
        gemSatchel = new U_GemSatchel(provider);
        gemTote = new U_GemTote(provider);
        gemSack = new U_GemSack(provider);
    }

    void fire(final String message, final U_AbstractGemContainer... containers) {
        final CustomChatMessage event = new CustomChatMessage(ChatMessageType.GAMEMESSAGE, message);
        for (final U_AbstractGemContainer container : containers) {
            listener.trigger(event, container);
        }
    }

    int count(final U_AbstractGemContainer container, final int itemId) {
        final StorageItems storage = container.getStorage();
        return storage.count(itemId);
    }
}
