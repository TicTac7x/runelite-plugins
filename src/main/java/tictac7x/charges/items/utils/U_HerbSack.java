package tictac7x.charges.items.utils;

import net.runelite.api.*;
import net.runelite.api.widgets.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.enums.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.Provider;

import java.util.*;

import static tictac7x.charges.store.ids.ItemContainerId.*;

public class U_HerbSack extends ChargedItemWithStorageEmptyable {
    public U_HerbSack(Provider provider) {
        this(TicTac7xChargesImprovedConfig.herb_sack, ItemId.HERB_SACK, ItemId.HERB_SACK_OPEN, 30, provider);
    }

    protected U_HerbSack(String configKey, int itemId, int openItemId, int maxQuantity, Provider provider) {
        super(configKey, itemId, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(itemId),
            new TriggerItem(openItemId),
        };

        storage = storage.setMaximumIndividualQuantity(maxQuantity).storableItems(
            new StorableItem(ItemId.GRIMY_GUAM_LEAF).checkName("Guam leaf"),
            new StorableItem(ItemId.GRIMY_MARRENTILL).checkName("Marrentill"),
            new StorableItem(ItemId.GRIMY_TARROMIN).checkName("Tarromin"),
            new StorableItem(ItemId.GRIMY_HARRALANDER).checkName("Harralander"),
            new StorableItem(ItemId.GRIMY_RANARR_WEED).checkName("Ranarr weed"),
            new StorableItem(ItemId.GRIMY_TOADFLAX).checkName("Toadflax"),
            new StorableItem(ItemId.GRIMY_IRIT_LEAF).checkName("Irit leaf"),
            new StorableItem(ItemId.GRIMY_AVANTOE).checkName("Avantoe"),
            new StorableItem(ItemId.GRIMY_KWUARM).checkName("Kwuarm"),
            new StorableItem(ItemId.GRIMY_SNAPDRAGON).checkName("Snapdragon"),
            new StorableItem(ItemId.GRIMY_HUASCA).checkName("Huasca"),
            new StorableItem(ItemId.GRIMY_CADANTINE).checkName("Cadantine"),
            new StorableItem(ItemId.GRIMY_LANTADYME).checkName("Lantadyme"),
            new StorableItem(ItemId.GRIMY_DWARF_WEED).checkName("Dwarf weed"),
            new StorableItem(ItemId.GRIMY_TORSTOL).checkName("Torstol")
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
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onMenuOption("Fill"),

            // Empty to inventory.
            new OnItemContainerChanged(INVENTORY).emptyStorageToInventory().onMenuOption("Empty"),

            // Empty to bank.
            new OnItemContainerChanged(BANK).emptyStorageToBank().onMenuOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank),

            // Empty from deposit box.
            new OnMenuOptionClicked(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).onItemClick().isWidgetVisible(WidgetId.DEPOSIT_BOX).emptyStorage(),

            // Replace "Empty" with proper "Empty to bank".
            new OnMenuEntryAdded("Empty").replaceOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Hide destroy option.
            new OnMenuEntryAdded("Destroy").hide(),

            // Pick guam leaf.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Guam")
            .addToStorage(ItemId.GRIMY_GUAM_LEAF),

            // Pick marrentill.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Marrentill")
            .addToStorage(ItemId.GRIMY_MARRENTILL),

            // Pick tarromin.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Tarromin")
            .addToStorage(ItemId.GRIMY_TARROMIN),

            // Pick harralander.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Harralander")
            .addToStorage(ItemId.GRIMY_HARRALANDER),

            // Pick ranarr.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Ranarr weed")
            .addToStorage(ItemId.GRIMY_RANARR_WEED),

            // Pick irit leaf.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Irit")
            .addToStorage(ItemId.GRIMY_IRIT_LEAF),

            // Pick avantoe.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Avantoe")
            .addToStorage(ItemId.GRIMY_AVANTOE),

            // Pick toadflax.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Toadflax")
            .addToStorage(ItemId.GRIMY_TOADFLAX),

            // Pick kwuarm.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Kwuarm")
            .addToStorage(ItemId.GRIMY_KWUARM),

            // Pick huasca.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Huasca")
            .addToStorage(ItemId.GRIMY_HUASCA),

            // Pick cadantine.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Cadantine")
            .addToStorage(ItemId.GRIMY_CADANTINE),

            // Pick lantadyme.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Lantadyme")
            .addToStorage(ItemId.GRIMY_LANTADYME),

            // Pick dwarf weed.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Dwarf weed")
            .addToStorage(ItemId.GRIMY_DWARF_WEED),

            // Pick torstol.
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Torstol")
            .addToStorage(ItemId.GRIMY_TORSTOL),

            // Pick snapdragon
            new OnXpDrop(Skill.FARMING).requiredItem(openItemId).onMenuOption("Pick").onMenuTarget("Snapdragon")
            .addToStorage(ItemId.GRIMY_SNAPDRAGON)
        ));
    }
}
