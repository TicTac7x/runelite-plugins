package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.Provider;

public class U_ImpInABox extends ChargedItemWithStatus {
    public U_ImpInABox(Provider provider) {
        super(TicTac7xChargesImprovedConfig.imp_in_a_box, ItemID.MAGIC_IMP_BOX_HALF, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.MAGIC_IMP_BOX_HALF).fixedCharges(1),
            new TriggerItem(ItemID.MAGIC_IMP_BOX_FULL).fixedCharges(2),
        };
    }
}
