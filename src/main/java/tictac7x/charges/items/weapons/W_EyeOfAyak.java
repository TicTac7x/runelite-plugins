package tictac7x.charges.items.weapons;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnAutoChargeMessage;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

import java.util.List;

public class W_EyeOfAyak  extends ChargedItem {
    public W_EyeOfAyak(Provider provider) {
        super(TicTac7xChargesImprovedConfig.eye_of_ayak, ItemId.EYE_OF_AYAK, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.EYE_OF_AYAK_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.EYE_OF_AYAK),
        };

        /*
         * Can be charged with either runes or demon tears but not both.
         * TODO? If the Eye is empty or currently charged with demon tears,
         * it will passively gain 10-20 charges upon defeating the Doom of Mokhaiotl.
         * No game message is displayed when this occurs.
         * If the player has multiple Eyes of Ayak in their inventory upon defeating the Doom of Mokhaiotl,
         * only one of them will be charged.
         */

        this.triggers.addAll(List.of(
            // Check
            new OnChatMessage("The Eye of Ayak has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Charge
            new OnChatMessage("The Eye of Ayak has been charged with (runes|demon tears). It currently has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Uncharge
            new OnChatMessage("You uncharge the Eye of Ayak.").setFixedCharges(0),

            // Attack
            new OnGraphicChanged(12397).decreaseCharges(1),

            // Special attack
            new OnGraphicChanged(12394).decreaseCharges(1),

            // Auto-charge
            new OnAutoChargeMessage("Eye of Ayak", "Death rune", 0.5, this),
            new OnAutoChargeMessage("Eye of Ayak", "Demon tear", 1, this)
        ));
    }
}
