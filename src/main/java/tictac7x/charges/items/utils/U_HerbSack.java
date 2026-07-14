package tictac7x.charges.items.utils;

import net.runelite.api.*;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class U_HerbSack extends ChargedItemWithStorageEmptyable {
    public U_HerbSack(Provider provider) {
        this(TicTac7xChargesImprovedConfig.herb_sack, ItemID.SLAYER_HERB_SACK, ItemID.SLAYER_HERB_SACK_OPEN, 30, provider);
    }

    protected U_HerbSack(String configKey, int itemId, int openItemId, int maxQuantity, Provider provider) {
        super(configKey, itemId, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(itemId),
            new TriggerItem(openItemId),
        };

        storage = storage.setMaximumIndividualQuantity(maxQuantity).storableItems(
            new StorableItem (ItemID.UNIDENTIFIED_GUAM).checkName("Guam leaf"),
            new StorableItem (ItemID.UNIDENTIFIED_MARENTILL).checkName("Marrentill"),
            new StorableItem (ItemID.UNIDENTIFIED_TARROMIN).checkName("Tarromin"),
            new StorableItem (ItemID.UNIDENTIFIED_HARRALANDER).checkName("Harralander"),
            new StorableItem (ItemID.UNIDENTIFIED_RANARR).checkName("Ranarr weed"),
            new StorableItem (ItemID.UNIDENTIFIED_TOADFLAX).checkName("Toadflax"),
            new StorableItem (ItemID.UNIDENTIFIED_IRIT).checkName("Irit leaf"),
            new StorableItem (ItemID.UNIDENTIFIED_AVANTOE).checkName("Avantoe"),
            new StorableItem (ItemID.UNIDENTIFIED_KWUARM).checkName("Kwuarm"),
            new StorableItem (ItemID.UNIDENTIFIED_SNAPDRAGON).checkName("Snapdragon"),
            new StorableItem (ItemID.UNIDENTIFIED_HUASCA).checkName("Huasca"),
            new StorableItem (ItemID.UNIDENTIFIED_CADANTINE).checkName("Cadantine"),
            new StorableItem (ItemID.UNIDENTIFIED_LANTADYME).checkName("Lantadyme"),
            new StorableItem (ItemID.UNIDENTIFIED_DWARF_WEED).checkName("Dwarf weed"),
            new StorableItem (ItemID.UNIDENTIFIED_TORSTOL).checkName("Torstol")
        );

        this.triggers.addAll(List.of(
            // Check or empty.
            new OnChatMessage("The herb sack is empty.").emptyStorage(),

            // Pickup.
            new OnChatMessage("You put the Grimy (?<herb>.+) herb into your herb sack.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("herb"), 1));
            }),

            // Check.
            new OnChatMessage("You look in your herb sack and see:").emptyStorage(),
            new OnChatMessage("(?<quantity>.+) x Grimy (?<herb>.+)").matcherConsumer(m -> {
                storage.put(getStorageItemFromName(m.group("herb"), Integer.parseInt(m.group("quantity"))));
            }),

            // Fill from inventory.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onMenuOption("Fill"),

            // Empty to inventory.
            new OnItemContainerChanged(InventoryID.INV).emptyStorageToInventory().onMenuOption("Empty"),

            // Empty to bank.
            new OnItemContainerChanged(InventoryID.BANK).emptyStorageToBank().onMenuOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank),

            // Empty from deposit box.
            new OnMenuOptionClicked(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).onItemClick().isWidgetVisible(WidgetId.DEPOSIT_BOX).emptyStorage(),

            // Replace "Empty" with proper "Empty to bank".
            new OnMenuEntryAdded("Empty").replaceOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Hide destroy option.
            new OnMenuEntryAdded("Destroy").hide(),

            // Pick guam leaf.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Guam")
            .addToStorage (ItemID.UNIDENTIFIED_GUAM),

            // Pick marrentill.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Marrentill")
            .addToStorage (ItemID.UNIDENTIFIED_MARENTILL),

            // Pick tarromin.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Tarromin")
            .addToStorage (ItemID.UNIDENTIFIED_TARROMIN),

            // Pick harralander.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Harralander")
            .addToStorage (ItemID.UNIDENTIFIED_HARRALANDER),

            // Pick ranarr.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Ranarr weed")
            .addToStorage (ItemID.UNIDENTIFIED_RANARR),

            // Pick irit leaf.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Irit")
            .addToStorage (ItemID.UNIDENTIFIED_IRIT),

            // Pick avantoe.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Avantoe")
            .addToStorage (ItemID.UNIDENTIFIED_AVANTOE),

            // Pick toadflax.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Toadflax")
            .addToStorage (ItemID.UNIDENTIFIED_TOADFLAX),

            // Pick kwuarm.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Kwuarm")
            .addToStorage (ItemID.UNIDENTIFIED_KWUARM),

            // Pick huasca.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Huasca")
            .addToStorage (ItemID.UNIDENTIFIED_HUASCA),

            // Pick cadantine.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Cadantine")
            .addToStorage (ItemID.UNIDENTIFIED_CADANTINE),

            // Pick lantadyme.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Lantadyme")
            .addToStorage (ItemID.UNIDENTIFIED_LANTADYME),

            // Pick dwarf weed.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Dwarf weed")
            .addToStorage (ItemID.UNIDENTIFIED_DWARF_WEED),

            // Pick torstol.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Torstol")
            .addToStorage (ItemID.UNIDENTIFIED_TORSTOL),

            // Pick snapdragon
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Snapdragon")
            .addToStorage (ItemID.UNIDENTIFIED_SNAPDRAGON)
        ));
    }
}
