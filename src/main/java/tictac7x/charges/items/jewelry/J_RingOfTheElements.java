package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

public class J_RingOfTheElements extends ChargedItem {
    public J_RingOfTheElements(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.ring_of_the_elements, ItemId.RING_OF_THE_ELEMENTS, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.RING_OF_THE_ELEMENTS),
            new TriggerItem(ItemId.RING_OF_THE_ELEMENTS_UNCHARGED).fixedCharges(0),
        };

        this.triggers = new TriggerBase[] {
            // Teleport.
            new OnVarbitChanged(13707).setDynamically(),

            // Unified menu entry.
            new OnMenuEntryAdded("Rub").replaceOption("Teleport"),

            // Last destination replaced with actual altar.
            new OnMenuEntryAdded("Last Destination").replaceOption("Air Altar").replaceTarget("Ring of the elements", "").varbitCheck(13708, 1),
            new OnMenuEntryAdded("Last Destination").replaceOption("Water Altar").replaceTarget("Ring of the elements", "").varbitCheck(13708, 2),
            new OnMenuEntryAdded("Last Destination").replaceOption("Earth Altar").replaceTarget("Ring of the elements", "").varbitCheck(13708, 3),
            new OnMenuEntryAdded("Last Destination").replaceOption("Fire Altar").replaceTarget("Ring of the elements", "").varbitCheck(13708, 4),
        };
    }
}