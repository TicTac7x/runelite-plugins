package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Provider;

public class J_Camulet extends ChargedItem {
    public J_Camulet(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.camulet, ItemId.CAMULET, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CAMULET),
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your Camulet has one charge left.").setFixedCharges(1),
            new OnChatMessage("Your Camulet has (?<charges>.+) charges left.").setDynamicallyCharges(),

            // Recharge.
            new OnChatMessage("You recharge the Camulet using camel dung. Yuck!").setFixedCharges(4),

            // Trying to charge fully charged.
            new OnChatMessage("The Camulet is already fully charged.").setFixedCharges(4),

            // Unlimited charges.
            new OnChatMessage("The Camulet has unlimited charges.").setFixedCharges(Charges.UNLIMITED),

            // Replace check.
            new OnMenuEntryAdded("Check-charge").replaceOption("Check"),

            // Replace rub
            new OnMenuEntryAdded("Rub").replaceOption("Teleport"),
        };
    }
}
