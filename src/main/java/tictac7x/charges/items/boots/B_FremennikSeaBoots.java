package tictac7x.charges.items.boots;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.List;

public class B_FremennikSeaBoots extends ChargedItem {
    public B_FremennikSeaBoots(Provider provider) {
        super(TicTac7xChargesImprovedConfig.fremennik_sea_boots, ItemId.FREMENNIK_SEA_BOOTS_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.FREMENNIK_SEA_BOOTS_1),
            new TriggerItem(ItemId.FREMENNIK_SEA_BOOTS_2),
            new TriggerItem(ItemId.FREMENNIK_SEA_BOOTS_3),
            new TriggerItem(ItemId.FREMENNIK_SEA_BOOTS_4).unlimitedCharges(),
        };

        this.triggers.addAll(List.of(
            // Try to teleport while empty.
            new OnChatMessage("You have already used your available teleport for today. Try again tomorrow when the boots have recharged.").setFixedCharges(0),

            // Teleport.
            new OnGraphicChanged(111).onItemClick().decreaseCharges(1),

            // Daily reset.
            new OnResetDaily().specificItem(ItemId.FREMENNIK_SEA_BOOTS_1).setFixedCharges(1),
            new OnResetDaily().specificItem(ItemId.FREMENNIK_SEA_BOOTS_2).setFixedCharges(3),
            new OnResetDaily().specificItem(ItemId.FREMENNIK_SEA_BOOTS_3).setFixedCharges(5)
        ));
    }
}