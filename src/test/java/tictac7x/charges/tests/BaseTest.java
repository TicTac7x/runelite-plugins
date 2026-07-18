package tictac7x.charges.tests;

import com.google.gson.*;
import net.runelite.api.*;
import net.runelite.api.gameval.InventoryID;
import net.runelite.client.*;
import net.runelite.client.callback.*;
import net.runelite.client.chat.*;
import net.runelite.client.config.*;
import net.runelite.client.game.*;
import net.runelite.client.plugins.*;
import net.runelite.client.ui.overlay.infobox.*;
import net.runelite.client.ui.overlay.tooltip.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.junit.*;
import tictac7x.charges.*;
import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.store.*;

import java.awt.*;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseTest {
    @Mock
    Client client;

    @Mock
    ClientThread clientThread;

    @Mock
    PluginManager pluginManager;

    @Mock
    ConfigManager configManager;

    @Mock
    ItemManager itemManager;

    @Mock
    InfoBoxManager infoBoxManager;

    @Mock
    ChatMessageManager chatMessageManager;

    @Mock
    TooltipManager tooltipManager;

    @Mock
    Notifier notifier;

    @Mock
    TicTac7xChargesImprovedPlugin plugin;

    @Mock
    TicTac7xChargesImprovedConfig config;

    Provider provider;
    Store store;

    @Before
    public void setup() {
        store = new Store(client, itemManager, configManager);
        provider = new Provider(client, clientThread, pluginManager, configManager, itemManager, infoBoxManager, chatMessageManager, tooltipManager, notifier, plugin, config, store, new Gson());
        store.addProvider(provider);

        when(config.getColorDefault()).thenReturn(Color.WHITE);
        when(config.getColorEmpty()).thenReturn(Color.RED);
    }

    public void setupInventoryItem(ChargedItemBase chargedItem) {
        setupItem(chargedItem, InventoryID.INV);
    }

    public void setupEquipmentItem(ChargedItemBase chargedItem) {
        setupItem(chargedItem, InventoryID.WORN);
    }

    private void setupItem(ChargedItemBase chargedItem, int inventoryId) {
        store.setChargedItems(new ChargedItemBase[]{chargedItem});
        store.onItemContainerChanged(new CustomItemContainerChanged(inventoryId, List.of(new StorageItem(chargedItem.itemId, 1))));
    }
}
