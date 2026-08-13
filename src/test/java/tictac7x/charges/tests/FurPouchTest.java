package tictac7x.charges.tests;

import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import org.junit.*;
import tictac7x.charges.events.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.items.jewelry.*;
import tictac7x.charges.items.utils.*;

import java.util.*;

import static org.junit.Assert.*;

public class FurPouchTest extends BaseTest {
    @Test
    public void FurPouch() {
        U_FurPouch furPouch = new U_FurPouch(provider);
        setupInventoryItem(furPouch);

        // Fill from inventory.
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.INV, List.of(
            new StorageItem(ItemID.HG_FURPOUCH_LARGE_OPEN),
            new StorageItem(ItemID.GOAT_PIT_FUR, 3)
        )));
        store.onMenuOptionClicked(new CustomMenuOptionClicked(
            -1, "Large fur pouch", "Fill", -1, "", ItemID.HG_FURPOUCH_LARGE, -1
        ));
        store.onItemContainerChanged(new CustomItemContainerChanged(InventoryID.INV, List.of(
            new StorageItem(ItemID.HG_FURPOUCH_LARGE_OPEN)
        )));
        assertEquals(3, furPouch.storage.getStorage().count(ItemID.GOAT_PIT_FUR));

        // Catch dashed kebbit.
        store.onStatChanged(new StatChanged(Skill.HUNTER, 7_444_523, 93, 93));
        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "You retrieve the falcon as well as the fur of the dead kebbit."));
        store.onStatChanged(new StatChanged(Skill.HUNTER, 7_444_523 + 156, 93, 93));

        assertEquals(3, furPouch.storage.getStorage().count(ItemID.GOAT_PIT_FUR));
        assertEquals(1, furPouch.storage.getStorage().count(ItemID.HUNTINGBEAST_SPEEDY2_FUR));

        // Golem crafting.
        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "You have crafted 1 golem on Wyrmscraig."));
        assertEquals(3, furPouch.storage.getStorage().count(ItemID.GOAT_PIT_FUR));
        assertEquals(0, furPouch.storage.getStorage().count(ItemID.HUNTINGBEAST_SPEEDY2_FUR));
        store.onChatMessage(new CustomChatMessage(ChatMessageType.GAMEMESSAGE, "You have crafted 2 golems on Wyrmscraig."));
        assertEquals(2, furPouch.storage.getStorage().count(ItemID.GOAT_PIT_FUR));
        assertEquals(0, furPouch.storage.getStorage().count(ItemID.HUNTINGBEAST_SPEEDY2_FUR));

        // Goat catching.
        store.onMenuOptionClicked(new CustomMenuOptionClicked(-1, "Goat Pit", "Clear", -1, "", -1, -1));
        store.onStatChanged(new StatChanged(Skill.HUNTER, 7_444_523 + 156 + 173, 93, 93));
        store.onGameTick(new GameTick());
        assertEquals(3, furPouch.storage.getStorage().count(ItemID.GOAT_PIT_FUR));

    }
}