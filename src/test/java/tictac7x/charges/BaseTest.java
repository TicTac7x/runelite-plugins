package tictac7x.charges;

import com.google.gson.*;
import net.runelite.api.*;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import org.junit.*;
import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.store.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BaseTest {
    Map<String, String> settings;
    MyClient myClient;
    MyItemManager itemManager;
    MyConfigManager configManager;
    MyConfig config;
    Store store;
    Provider provider;

    @Before
    public void setup() {
        config = new MyConfig(
            () -> false,
            () -> "test",
            () -> true,
            () -> "",
            () -> Color.GRAY,
            () -> Color.RED,
            () -> Color.GREEN,
            () -> Color.WHITE,
            () -> true,
            () -> true,
            () -> true,
            () -> true,
            () -> false,
            () -> TicTac7xChargesImprovedConfig.ItemOverlayLocation.BOTTOM_LEFT,
            () -> true,
            () -> true,
            () -> TicTac7xChargesImprovedConfig.CombatTimeDegradableStyle.PERCENTAGE,
            () -> 0,
            () -> TicTac7xChargesImprovedConfig.EscapeCrystalTimeRemainingUnit.SECONDS,
            () -> 0,
            () -> TicTac7xChargesImprovedConfig.ItemActivity.ACTIVATED,
            () -> Color.WHITE,
            () -> Color.YELLOW,
            () -> Color.ORANGE,
            () -> Color.RED,
            () -> 0
        );
        settings = new HashMap<>();
        myClient = new MyClient(
            (varbitId) -> 0,
            () -> 0,
            () -> GameState.LOGGED_IN,
            (actor -> true)
        );
        itemManager = new MyItemManager(
            (itemId) -> new MyItemComposition(0, "", true, false),
            (itemId) -> null
        );
        configManager = new MyConfigManager(
            (key, value) -> settings.put(key, (String) value),
            (key) -> settings.get(key)
        );

        store = new Store(myClient, itemManager, configManager);
        provider = new Provider(null, null, null, configManager, itemManager, null, null, null, null, null, config, store, new Gson());
        store.addProvider(provider);
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
