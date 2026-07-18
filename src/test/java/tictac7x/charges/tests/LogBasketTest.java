package tictac7x.charges.tests;

import net.runelite.api.gameval.*;
import org.junit.*;
import tictac7x.charges.items.capes.*;

import static org.junit.Assert.*;

public class LogBasketTest extends BaseTest {
    @Test
    public void LogBasket() {
        C_LogBasket logBasket = new C_LogBasket(provider);
        setupInventoryItem(logBasket);

        assertEquals(config.getColorDefault(), logBasket.getTotalTextColor());
        logBasket.storage.put(ItemID.WILLOW_LOGS, 28);
        assertEquals(config.getColorEmpty(), logBasket.getTotalTextColor());
    }
}