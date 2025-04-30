package tictac7x.charges.items.utils;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class U_TeleportCrystal extends ChargedItem {
    public U_TeleportCrystal(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.teleport_crystal, ItemId.TELEPORT_CRYSTAL_0, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TELEPORT_CRYSTAL_0).fixedCharges(0),
            new TriggerItem(ItemId.TELEPORT_CRYSTAL_1).fixedCharges(1),
            new TriggerItem(ItemId.TELEPORT_CRYSTAL_2).fixedCharges(2),
            new TriggerItem(ItemId.TELEPORT_CRYSTAL_3).fixedCharges(3),
            new TriggerItem(ItemId.TELEPORT_CRYSTAL_4).fixedCharges(4),
            new TriggerItem(ItemId.TELEPORT_CRYSTAL_5).fixedCharges(5),
        };
    }
}
