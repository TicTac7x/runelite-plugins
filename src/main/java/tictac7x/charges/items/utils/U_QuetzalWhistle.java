package tictac7x.charges.items.utils;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

import java.util.*;

public class U_QuetzalWhistle extends ChargedItem {
    public U_QuetzalWhistle(Provider provider) {
        super(TicTac7xChargesImprovedConfig.quetzal_whistle, ItemID.HG_QUETZALWHISTLE_BASIC, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.HG_QUETZALWHISTLE_BASIC).maxCharges(5),
            new TriggerItem(ItemID.HG_QUETZALWHISTLE_ENHANCED).maxCharges(20),
            new TriggerItem(ItemID.HG_QUETZALWHISTLE_PERFECTED).maxCharges(50),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your quetzal whistle has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Teleport.
            new OnAnimationChanged(AnimationID.HUMAN_QUETZAL_WHISTLE).decreaseCharges(1),

            // Teleport menu entry.
            new OnMenuEntryAdded("Signal").replaceOption("Teleport"),

            // Craft basic quetzal whistle.
            new OnChatMessage("You craft yourself a basic quetzal whistle.").setFixedCharges(0),

            // Fully charged.
            new OnChatMessage("Looks like the birds are all full for now. Make them work a bit before feeding them again!").requiredItem (ItemID.HG_QUETZALWHISTLE_BASIC).setFixedCharges(5),
            new OnChatMessage("Looks like the birds are all full for now. Make them work a bit before feeding them again!").requiredItem (ItemID.HG_QUETZALWHISTLE_ENHANCED).setFixedCharges(20),
            new OnChatMessage("Looks like the birds are all full for now. Make them work a bit before feeding them again!").requiredItem (ItemID.HG_QUETZALWHISTLE_PERFECTED).setFixedCharges(50),

            // Partially charged.
            new OnItemContainerChanged(InventoryID.INV).onMenuOption("Recharge-whistle").hasChatMessage("Soar Leader Pitri|There you go. Some whistle charges for you!").onInventoryDifference(itemsDifference -> {
                for (StorageItem item : itemsDifference.getItems()) {
                    switch (item.itemId) {
                        case ItemID.HG_SEEDSACK:
                        case ItemID.HUNTINGBEAST_WILD_MEAT:
                        case ItemID.HUNTINGBEAST_BARBED_MEAT:
                        case ItemID.HUNTING_LARUPIA_MEAT:
                            increaseCharges(Math.abs(item.getQuantity()));
                            break;
                        case ItemID.HUNTING_GRAAHK_MEAT:
                        case ItemID.HUNTING_KYATT_MEAT:
                        case ItemID.HUNTING_FENNECFOX_MEAT:
                            increaseCharges(Math.abs(item.getQuantity()) * 2);
                            break;
                        case ItemID.HUNTINGBEAST_SPEEDY2_MEAT:
                        case ItemID.HUNTING_ANTELOPESUN_MEAT:
                        case ItemID.HUNTING_ANTELOPEMOON_MEAT:
                            increaseCharges(Math.abs(item.getQuantity()) * 3);
                            break;
                    }
                }
            })
        ));
    }
}
