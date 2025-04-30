package tictac7x.charges.items.weapons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

public class W_VenatorBow extends ChargedItem {
    public W_VenatorBow(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.venator_bow, ItemId.VENATOR_BOW, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.VENATOR_BOW_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.VENATOR_BOW)
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your venator bow has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(2289).decreaseCharges(1),
        };
    }
}
