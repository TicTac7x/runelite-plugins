package tictac7x.charges.items.weapons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class W_IbansStaff extends ChargedItem {
    public W_IbansStaff(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.ibans_staff, ItemId.IBANS_STAFF, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.IBANS_STAFF),
            new TriggerItem(ItemId.IBANS_STAFF_BROKEN),
            new TriggerItem(ItemId.IBANS_STAFF_UPGRADED),
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("You have (?<charges>.+) charges left on the staff.").setDynamicallyCharges().onItemClick(),

            // Attack.
            new OnGraphicChanged(87).isEquipped().decreaseCharges(1),
        };
    }
}
