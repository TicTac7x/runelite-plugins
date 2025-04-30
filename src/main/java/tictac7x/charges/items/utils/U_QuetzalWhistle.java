package tictac7x.charges.items.utils;

import tictac7x.charges.store.*;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnAnimationChanged;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnItemContainerChanged;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.AnimationId;
import tictac7x.charges.store.ids.ItemContainerId;
import tictac7x.charges.store.ids.ItemId;

public class U_QuetzalWhistle extends ChargedItem {
    public U_QuetzalWhistle(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.quetzal_whistle, ItemId.QUETZAL_WHISTLE_BASIC, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.QUETZAL_WHISTLE_BASIC).maxCharges(5),
            new TriggerItem(ItemId.QUETZAL_WHISTLE_ENHANCED).maxCharges(20),
            new TriggerItem(ItemId.QUETZAL_WHISTLE_PERFECTED).maxCharges(50),
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your quetzal whistle has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Teleport.
            new OnAnimationChanged(AnimationId.QUETZAL_WHISTLE_BIRD).decreaseCharges(1),

            // Teleport menu entry.
            new OnMenuEntryAdded("Signal").replaceOption("Teleport"),

            // Craft basic quetzal whistle.
            new OnChatMessage("You craft yourself a basic quetzal whistle.").setFixedCharges(0),

            // Fully charged.
            new OnChatMessage("Looks like the birds are all full for now. Make them work a bit before feeding them again!").requiredItem(ItemId.QUETZAL_WHISTLE_BASIC).setFixedCharges(5),
            new OnChatMessage("Looks like the birds are all full for now. Make them work a bit before feeding them again!").requiredItem(ItemId.QUETZAL_WHISTLE_ENHANCED).setFixedCharges(20),
            new OnChatMessage("Looks like the birds are all full for now. Make them work a bit before feeding them again!").requiredItem(ItemId.QUETZAL_WHISTLE_PERFECTED).setFixedCharges(50),

            // Partially charged.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).hasChatMessage("Soar Leader Pitri|There you go. Some whistle charges for you!").onInventoryDifference(itemsDifference -> {
                for (final StorageItem item : itemsDifference.getItems()) {
                    switch (item.getId()) {
                        case ItemId.QUETZAL_FEED:
                        case ItemId.RAW_WILD_KEBBIT:
                        case ItemId.RAW_BARBTAILED_KEBBIT:
                        case ItemId.RAW_LARUPIA:
                            increaseCharges(Math.abs(item.getQuantity()));
                            break;
                        case ItemId.RAW_GRAAHK:
                        case ItemId.RAW_KYATT:
                        case ItemId.RAW_PYRE_FOX:
                            increaseCharges(Math.abs(item.getQuantity()) * 2);
                            break;
                        case ItemId.RAW_DASHING_KEBBIT:
                        case ItemId.RAW_SUNLIGHT_ANTELOPE:
                        case ItemId.RAW_MOONLIGHT_ANTELOPE:
                            increaseCharges(Math.abs(item.getQuantity()) * 3);
                            break;
                    }
                }
            })
        };
    }
}
