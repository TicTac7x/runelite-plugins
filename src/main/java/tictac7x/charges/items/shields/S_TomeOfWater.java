package tictac7x.charges.items.shields;

import tictac7x.charges.item.triggers.OnAutoChargeMessage;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.util.List;

public class S_TomeOfWater extends ChargedItem {
    public S_TomeOfWater(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.tome_of_water, ItemId.TOME_OF_WATER, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TOME_OF_WATER_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.TOME_OF_WATER).needsToBeEquipped(),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your tome currently holds (?<charges>.+) charges?.").setDynamicallyCharges().onItemClick(),

            // Attack with regular spellbook water spells.
            new OnGraphicChanged(93, 120, 135, 161, 1458).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Tome of water", "Soaked page", 20, this)
        ));
    }
}
