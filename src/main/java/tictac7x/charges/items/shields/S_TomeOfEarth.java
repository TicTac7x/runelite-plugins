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

public class S_TomeOfEarth extends ChargedItem {
    public S_TomeOfEarth(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.tome_of_earth, ItemId.TOME_OF_EARTH, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TOME_OF_EARTH_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.TOME_OF_EARTH).needsToBeEquipped(),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your tome currently holds (?<charges>.+) charges?.").setDynamicallyCharges().onItemClick(),

            // Attack with regular spellbook earth spells.
            new OnGraphicChanged(96, 123, 138, 164, 1461).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Tome of earth", "Soiled page", 20, this)
        ));
    }
}
