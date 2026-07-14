package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.Provider;

import java.util.List;

public class U_GricollersCan extends ChargedItem {
    public U_GricollersCan(Provider provider) {
        super(TicTac7xChargesImprovedConfig.gricollers_can, ItemId.GRICOLLERS_CAN, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.GRICOLLERS_CAN),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Watering can charges remaining: (?<charges>.+)%").setDynamicallyCharges().onItemClick(),

            // Water inventory item.
            new OnChatMessage("You water").onItemClick().decreaseCharges(1),

            // Fill.
            new OnChatMessage("You fill the watering can").onItemClick().setFixedCharges(1000),

            // Water.
            new OnGraphicChanged(410).decreaseCharges(1)
        ));
    }
}
