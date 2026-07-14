package tictac7x.charges.items.jewelry;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class J_AbyssalBracelet extends ChargedItem {
    public J_AbyssalBracelet(Provider provider) {
        super(TicTac7xChargesImprovedConfig.abyssal_bracelet, ItemId.ABYSSAL_BRACELET_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ABYSSAL_BRACELET_1).fixedCharges(1),
            new TriggerItem(ItemId.ABYSSAL_BRACELET_2).fixedCharges(2),
            new TriggerItem(ItemId.ABYSSAL_BRACELET_3).fixedCharges(3),
            new TriggerItem(ItemId.ABYSSAL_BRACELET_4).fixedCharges(4),
            new TriggerItem(ItemId.ABYSSAL_BRACELET_5).fixedCharges(5),
        };
    }
}
