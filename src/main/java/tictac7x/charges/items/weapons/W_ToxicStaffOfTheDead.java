package tictac7x.charges.items.weapons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class W_ToxicStaffOfTheDead extends ChargedItem {
    public W_ToxicStaffOfTheDead(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.toxic_staff_of_the_dead, ItemId.TOXIC_STAFF_OF_THE_DEAD, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TOXIC_STAFF_OF_THE_DEAD_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.TOXIC_STAFF_OF_THE_DEAD)
        };

        this.triggers = new TriggerBase[] {
            new OnChatMessage("Scales: (?<charges>.+)").setDynamicallyCharges(),
        };
    }
}
