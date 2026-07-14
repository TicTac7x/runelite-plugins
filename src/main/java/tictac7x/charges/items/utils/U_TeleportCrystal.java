package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class U_TeleportCrystal extends ChargedItem {
    public U_TeleportCrystal(Provider provider) {
        super(TicTac7xChargesImprovedConfig.teleport_crystal, ItemID.GAUNTLET_TELEPORT_CRYSTAL, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.GAUNTLET_TELEPORT_CRYSTAL).fixedCharges(0),
            new TriggerItem(ItemID.MOURNING_TELEPORT_CRYSTAL_1).fixedCharges(1),
            new TriggerItem(ItemID.MOURNING_TELEPORT_CRYSTAL_2).fixedCharges(2),
            new TriggerItem(ItemID.MOURNING_TELEPORT_CRYSTAL_3).fixedCharges(3),
            new TriggerItem(ItemID.MOURNING_TELEPORT_CRYSTAL_4).fixedCharges(4),
            new TriggerItem(ItemID.MOURNING_TELEPORT_CRYSTAL_5).fixedCharges(5),
        };
    }
}
