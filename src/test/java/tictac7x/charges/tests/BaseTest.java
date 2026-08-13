package tictac7x.charges.tests;

import com.google.gson.*;
import net.runelite.api.*;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
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
import java.util.*;
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
        Map<String, String> settings = new HashMap<>();

        store = new Store(client, itemManager, configManager);
        provider = new Provider(client, clientThread, pluginManager, configManager, itemManager, infoBoxManager, chatMessageManager, tooltipManager, notifier, plugin, config, store, new Gson());
        store.addProvider(provider);

        lenient().when(config.getColorDefault()).thenReturn(Color.WHITE);
        lenient().when(config.getColorEmpty()).thenReturn(Color.RED);
        lenient().when(config.getColorActivated()).thenReturn(Color.GREEN);

        lenient().doAnswer(invocation -> {
            String key = invocation.getArgument(1);
            Integer value = invocation.getArgument(2);

            settings.put(key, String.valueOf(value));
            return null;
        }).when(configManager).setConfiguration(
            eq(TicTac7xChargesImprovedConfig.group),
            anyString(),
            anyInt()
        );
        lenient().when(configManager.getConfiguration(eq(TicTac7xChargesImprovedConfig.group), anyString()))
        .thenAnswer(invocation -> {
            String key = invocation.getArgument(1);
            return settings.get(key);
        });

        when(itemManager.getItemComposition(anyInt())).thenAnswer(invocation -> {
            int itemId = invocation.getArgument(0);

            ItemComposition composition = mock(ItemComposition.class);

            String name;
            switch (itemId) {
                case ItemID.UNCUT_OPAL:
                    name = "Uncut opal";
                    break;
                case ItemID.UNCUT_JADE:
                    name = "Uncut jade";
                    break;
                case ItemID.UNCUT_RED_TOPAZ:
                    name = "Uncut red topaz";
                    break;
                case ItemID.UNCUT_SAPPHIRE:
                    name = "Uncut sapphire";
                    break;
                case ItemID.UNCUT_EMERALD:
                    name = "Uncut emerald";
                    break;
                case ItemID.UNCUT_RUBY:
                    name = "Uncut ruby";
                    break;
                case ItemID.UNCUT_DIAMOND:
                    name = "Uncut diamond";
                    break;
                case ItemID.UNCUT_DRAGONSTONE:
                    name = "Uncut dragonstone";
                    break;
                case ItemID.UNCUT_ONYX:
                    name = "Uncut onyx";
                    break;
                case ItemID.UNCUT_ZENYTE:
                    name = "Uncut zenyte";
                    break;
                default:
                    name = null;
                    break;
            }

            when(composition.getName()).thenReturn(name);
            return composition;
        });
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
