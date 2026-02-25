package tictac7x.charges.item.triggers;

import tictac7x.charges.item.ChargedItem;

public class OnAutoChargeMessage extends OnChatMessage {
    public OnAutoChargeMessage(final String itemName, final String usedItemName, final double multiplier, final ChargedItem chargedItem) {
        super("The banker charges your " + itemName + " using (?<amount>.+?)x " + usedItemName + ".*");

        this.matcherConsumer(m -> {
            chargedItem.increaseCharges((int) Math.round((Integer.parseInt(m.group("amount")) * multiplier)));
        });
    }
}
