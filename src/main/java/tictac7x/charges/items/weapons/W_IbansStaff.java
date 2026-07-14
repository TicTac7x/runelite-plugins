package tictac7x.charges.items.weapons;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class W_IbansStaff extends ChargedItem {
    public W_IbansStaff(Provider provider) {
        super(TicTac7xChargesImprovedConfig.ibans_staff, ItemId.IBANS_STAFF, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.IBANS_STAFF),
            new TriggerItem(ItemId.IBANS_STAFF_BROKEN),
            new TriggerItem(ItemId.IBANS_STAFF_UPGRADED),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("You have (?<charges>.+) charges left on the staff.").setDynamicallyCharges().onItemClick(),

            // Attack.
            new OnGraphicChanged(87).isEquipped().decreaseCharges(1)
        ));
    }
}
