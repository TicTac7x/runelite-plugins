package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.Provider;

import java.util.List;

public class U_GricollersCan extends ChargedItem {
    public U_GricollersCan(Provider provider) {
        super(TicTac7xChargesImprovedConfig.gricollers_can, ItemID.ZEAH_WATERINGCAN, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.ZEAH_WATERINGCAN),
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
