package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class J_RingOfWealth extends ChargedItem {
    public J_RingOfWealth(Provider provider) {
        super(TicTac7xChargesImprovedConfig.ring_of_wealth, ItemID.RING_OF_WEALTH, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.RING_OF_WEALTH).fixedCharges(0),
            new TriggerItem(ItemID.RING_OF_WEALTH_1).fixedCharges(1),
            new TriggerItem(ItemID.RING_OF_WEALTH_2).fixedCharges(2),
            new TriggerItem(ItemID.RING_OF_WEALTH_3).fixedCharges(3),
            new TriggerItem(ItemID.RING_OF_WEALTH_4).fixedCharges(4),
            new TriggerItem(ItemID.RING_OF_WEALTH_5).fixedCharges(5),
            new TriggerItem(ItemID.RING_OF_WEALTH_I).fixedCharges(0),
            new TriggerItem(ItemID.RING_OF_WEALTH_I1).fixedCharges(1),
            new TriggerItem(ItemID.RING_OF_WEALTH_I2).fixedCharges(2),
            new TriggerItem(ItemID.RING_OF_WEALTH_I3).fixedCharges(3),
            new TriggerItem(ItemID.RING_OF_WEALTH_I4).fixedCharges(4),
            new TriggerItem(ItemID.RING_OF_WEALTH_I5).fixedCharges(5),
        };
    }
}
