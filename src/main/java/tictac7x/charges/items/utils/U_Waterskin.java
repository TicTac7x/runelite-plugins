package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class U_Waterskin extends ChargedItem {
    public U_Waterskin(Provider provider) {
        super(TicTac7xChargesImprovedConfig.waterskin, ItemID.WATER_SKIN0, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.WATER_SKIN0).fixedCharges(0),
            new TriggerItem(ItemID.WATER_SKIN1).fixedCharges(1),
            new TriggerItem(ItemID.WATER_SKIN2).fixedCharges(2),
            new TriggerItem(ItemID.WATER_SKIN3).fixedCharges(3),
            new TriggerItem(ItemID.WATER_SKIN4).fixedCharges(4),
        };
    }
}
