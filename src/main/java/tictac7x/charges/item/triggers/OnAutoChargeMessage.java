package tictac7x.charges.item.triggers;

import tictac7x.charges.item.*;

public class OnAutoChargeMessage extends OnChatMessage {
    public OnAutoChargeMessage(String itemName, String usedItemName, double multiplier, ChargedItem chargedItem) {
        super("The banker charges your " + itemName + " using (?<amount>.+?)x " + usedItemName + ".*");

        this.matcherConsumer(m -> {
            chargedItem.increaseCharges((int) Math.round((Integer.parseInt(m.group("amount")) * multiplier)));
        });
    }

    public OnAutoChargeMessage(String itemName, String usedItemName, double multiplier, ChargedItemWithStorage chargedItemWithStorage, int storageItemId) {
        super("The banker charges your " + itemName + " using (?<amount>.+?)x " + usedItemName + ".*");

        this.matcherConsumer(m -> {
            chargedItemWithStorage.storage.add(storageItemId, (int) Math.round((Integer.parseInt(m.group("amount")) * multiplier)));
        });
    }
}
