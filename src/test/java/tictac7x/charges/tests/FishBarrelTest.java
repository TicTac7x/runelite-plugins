package tictac7x.charges.tests;

import net.runelite.api.Skill;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import org.junit.Test;
import tictac7x.charges.events.CustomItemContainerChanged;
import tictac7x.charges.events.CustomMenuOptionClicked;
import tictac7x.charges.events.CustomStatChanged;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.items.utils.U_FishBarrel;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class FishBarrelTest extends BaseTest {
    @Test
    public void FishBarrel() {
        U_FishBarrel fishBarrel = new U_FishBarrel(provider);
        setupInventoryItem(fishBarrel);
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.INV, List.of(
            new StorageItem(ItemID.FISH_BARREL_OPEN)
        )));

        store.onMenuOptionClicked(new CustomMenuOptionClicked(-1, "Fishing spot", "Catch", -1, "", -1, -1));

        store.onStatChanged(new CustomStatChanged(Skill.FISHING, 98, 12_443_747, 0));
        store.onStatChanged(new CustomStatChanged(Skill.FISHING, 98, 12_443_747 + 100, 100));

        assertEquals(1, fishBarrel.storage.getStorage().count(ItemID.AERIAL_FISHING_GREATER_SIREN));
    }
}