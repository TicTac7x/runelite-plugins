package tictac7x.charges.items.capes;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

import static tictac7x.charges.store.ids.ItemContainerId.*;

public class C_Coffin extends ChargedItemWithStorageEmptyable {
    public C_Coffin(Provider provider) {
        super(TicTac7xChargesImprovedConfig.coffin, ItemId.GOLD_COFFIN, provider);
        this.storage = storage.storableItems(
            new StorableItem(ItemId.LOAR_REMAINS).checkName("Loar"),
            new StorableItem(ItemId.PHRIN_REMAINS).checkName("Phrin"),
            new StorableItem(ItemId.RIYL_REMAINS).checkName("Riyl"),
            new StorableItem(ItemId.ASYN_REMAINS).checkName("Asyn"),
            new StorableItem(ItemId.FIYR_REMAINS).checkName("Fiyr"),
            new StorableItem(ItemId.URIUM_REMAINS).checkName("Urium")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BROKEN_COFFIN).fixedCharges(0),
            new TriggerItem(ItemId.BRONZE_COFFIN).maxCharges(3),
            new TriggerItem(ItemId.BRONZE_COFFIN_OPEN).maxCharges(3),
            new TriggerItem(ItemId.STEEL_COFFIN).maxCharges(8),
            new TriggerItem(ItemId.STEEL_COFFIN_OPEN).maxCharges(8),
            new TriggerItem(ItemId.BLACK_COFFIN).maxCharges(14),
            new TriggerItem(ItemId.BLACK_COFFIN_OPEN).maxCharges(14),
            new TriggerItem(ItemId.SILVER_COFFIN).maxCharges(20),
            new TriggerItem(ItemId.SILVER_COFFIN_OPEN).maxCharges(20),
            new TriggerItem(ItemId.GOLD_COFFIN).maxCharges(28),
            new TriggerItem(ItemId.GOLD_COFFIN_OPEN).maxCharges(28),
        };

        this.triggers.addAll(List.of(
            // Add remains to coffin.
            new OnChatMessage("You put the (?<remains>.+) remains into your open coffin.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("remains"), 1));
            }),

            // Check.
            new OnChatMessage("Loar (?<loar>.+) / Phrin (?<phrin>.+) / Riyl (?<riyl>.+) / Asyn (?<asyn>.+) / Fiyr (?<fiyr>.+) / Urium (?<urium>.+)").matcherConsumer(m -> {
                storage.clear();
                storage.put(ItemId.LOAR_REMAINS, Integer.parseInt(m.group("loar")));
                storage.put(ItemId.PHRIN_REMAINS, Integer.parseInt(m.group("phrin")));
                storage.put(ItemId.RIYL_REMAINS, Integer.parseInt(m.group("riyl")));
                storage.put(ItemId.ASYN_REMAINS, Integer.parseInt(m.group("asyn")));
                storage.put(ItemId.FIYR_REMAINS, Integer.parseInt(m.group("fiyr")));
                storage.put(ItemId.URIUM_REMAINS, Integer.parseInt(m.group("urium")));
            }),

            // Try to empty already empty.
            new OnChatMessage("Your coffin is empty.").onItemClick().emptyStorage(),

            // Fill from inventory.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onMenuOption("Fill"),

            // Use shades on coffin and vice versa.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseChargedItemOnStorageItem(storage.getStorableItems()),
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems())
        ));
    }
}
