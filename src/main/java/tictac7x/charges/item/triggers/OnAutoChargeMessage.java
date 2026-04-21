package tictac7x.charges.item.triggers;

import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.ChargedItemWithStorage;

public class OnAutoChargeMessage extends OnChatMessage {
    public OnAutoChargeMessage(final String itemName, final String usedItemName, final double multiplier, final ChargedItem chargedItem) {
        super("The banker charges your " + itemName + " using (?<amount>.+?)x " + usedItemName + ".*");

        this.matcherConsumer(m -> {
            chargedItem.increaseCharges((int) Math.round((Integer.parseInt(m.group("amount")) * multiplier)));
        });
    }

    public OnAutoChargeMessage(final String itemName, final String usedItemName, final double multiplier, final ChargedItemWithStorage chargedItemWithStorage, final int storageItemId) {
        super("The banker charges your " + itemName + " using (?<amount>.+?)x " + usedItemName + ".*");

        this.matcherConsumer(m -> {
            chargedItemWithStorage.storage.add(storageItemId, (int) Math.round((Integer.parseInt(m.group("amount")) * multiplier)));
        });
    }
}
