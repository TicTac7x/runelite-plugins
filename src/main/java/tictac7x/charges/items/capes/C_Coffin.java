package tictac7x.charges.items.capes;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

import java.util.*;

public class C_Coffin extends ChargedItemWithStorageEmptyable {
    public C_Coffin(Provider provider) {
        super(TicTac7xChargesImprovedConfig.coffin, ItemID.SHADES_COFFIN_GOLD, provider);
        this.storage = storage.storableItems(
            new StorableItem(ItemID.SHADE_BONES1).checkName("Loar"),
            new StorableItem(ItemID.SHADE_BONES2).checkName("Phrin"),
            new StorableItem(ItemID.SHADE_BONES3).checkName("Riyl"),
            new StorableItem(ItemID.SHADE_BONES4).checkName("Asyn"),
            new StorableItem(ItemID.SHADE_BONES5).checkName("Fiyr"),
            new StorableItem(ItemID.SHADE_BONES6).checkName("Urium")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.SHADES_COFFIN_BROKEN).fixedCharges(0),
            new TriggerItem(ItemID.SHADES_COFFIN_BRONZE).maxCharges(3),
            new TriggerItem(ItemID.SHADES_COFFIN_BRONZE_OPEN).maxCharges(3),
            new TriggerItem(ItemID.SHADES_COFFIN_STEEL).maxCharges(8),
            new TriggerItem(ItemID.SHADES_COFFIN_STEEL_OPEN).maxCharges(8),
            new TriggerItem(ItemID.SHADES_COFFIN_BLACK).maxCharges(14),
            new TriggerItem(ItemID.SHADES_COFFIN_BLACK_OPEN).maxCharges(14),
            new TriggerItem(ItemID.SHADES_COFFIN_SILVER).maxCharges(20),
            new TriggerItem(ItemID.SHADES_COFFIN_SILVER_OPEN).maxCharges(20),
            new TriggerItem(ItemID.SHADES_COFFIN_GOLD).maxCharges(28),
            new TriggerItem(ItemID.SHADES_COFFIN_GOLD_OPEN).maxCharges(28),
        };

        this.triggers.addAll(List.of(
            // Add remains to coffin.
            new OnChatMessage("You put the (?<remains>.+) remains into your open coffin.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("remains"), 1));
            }),

            // Check.
            new OnChatMessage("Loar (?<loar>.+) / Phrin (?<phrin>.+) / Riyl (?<riyl>.+) / Asyn (?<asyn>.+) / Fiyr (?<fiyr>.+) / Urium (?<urium>.+)").matcherConsumer(m -> {
                storage.clear();
                storage.put(ItemID.SHADE_BONES1, Integer.parseInt(m.group("loar")));
                storage.put(ItemID.SHADE_BONES2, Integer.parseInt(m.group("phrin")));
                storage.put(ItemID.SHADE_BONES3, Integer.parseInt(m.group("riyl")));
                storage.put(ItemID.SHADE_BONES4, Integer.parseInt(m.group("asyn")));
                storage.put(ItemID.SHADE_BONES5, Integer.parseInt(m.group("fiyr")));
                storage.put(ItemID.SHADE_BONES6, Integer.parseInt(m.group("urium")));
            }),

            // Try to empty already empty.
            new OnChatMessage("Your coffin is empty.").onItemClick().emptyStorage(),

            // Fill from inventory.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onMenuOption("Fill"),

            // Use shades on coffin and vice versa.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onUseChargedItemOnStorageItem(storage.getStorableItems()),
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems())
        ));
    }
}
