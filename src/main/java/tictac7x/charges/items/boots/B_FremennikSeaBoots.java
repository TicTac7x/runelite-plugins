package tictac7x.charges.items.boots;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.OnResetDaily;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ChargeId;
import tictac7x.charges.store.Provider;

public class B_FremennikSeaBoots extends ChargedItem {
    public B_FremennikSeaBoots(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.fremennik_sea_boots, ItemId.FREMENNIK_SEA_BOOTS_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.FREMENNIK_SEA_BOOTS_1),
            new TriggerItem(ItemId.FREMENNIK_SEA_BOOTS_2),
            new TriggerItem(ItemId.FREMENNIK_SEA_BOOTS_3),
            new TriggerItem(ItemId.FREMENNIK_SEA_BOOTS_4).fixedCharges(ChargeId.UNLIMITED),
        };

        this.triggers = new TriggerBase[]{
            // Try to teleport while empty.
            new OnChatMessage("You have already used your available teleport for today. Try again tomorrow when the boots have recharged.").setFixedCharges(0),

            // Teleport.
            new OnGraphicChanged(111).onItemClick().decreaseCharges(1),

            // Daily reset.
            new OnResetDaily().specificItem(ItemId.FREMENNIK_SEA_BOOTS_1).setFixedCharges(1),
            new OnResetDaily().specificItem(ItemId.FREMENNIK_SEA_BOOTS_2).setFixedCharges(3), // Updated from 1 to 3 charges https://oldschool.runescape.wiki/w/Update:Poll_85_-_Bridges,_Boots,_Ropes_%26_Roots#Fremennik_Sea_Boots
            new OnResetDaily().specificItem(ItemId.FREMENNIK_SEA_BOOTS_3).setFixedCharges(5), // Updated from 1 to 5 charges https://oldschool.runescape.wiki/w/Update:Poll_85_-_Bridges,_Boots,_Ropes_%26_Roots#Fremennik_Sea_Boots
        };
    }
}