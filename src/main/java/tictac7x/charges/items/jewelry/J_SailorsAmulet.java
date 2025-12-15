package tictac7x.charges.items.jewelry;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnMenuOptionClicked;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class J_SailorsAmulet extends ChargedItem {
    public J_SailorsAmulet(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.sailors_amulet, ItemId.SAILORS_AMULET, provider);

        this.items = new TriggerItem[]{
                new TriggerItem(ItemId.SAILORS_AMULET_UNCHARGED).fixedCharges(0),
                new TriggerItem(ItemId.SAILORS_AMULET)
        };

        this.triggers = new TriggerBase[]{
                // Check
                new OnChatMessage("(The|Your) amulet has (?<charges>.+) charges.")
                        .setDynamicallyCharges()
                        .onItemClick(),
                // Charge
                new OnChatMessage("You add \\d+ charges? to your amulet\\. It now has (?<charges>\\d+) charges?\\.")
                        .setDynamicallyCharges(),
                // Teleport
                new OnMenuOptionClicked("The Pandemonium", "Port Roberts", "Deepfin Point")
                        .onMenuOptionId(65540, 131076, 327684)
                        .decreaseCharges(1),
        };
    }
}
