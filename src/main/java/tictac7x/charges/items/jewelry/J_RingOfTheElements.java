package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

import java.util.*;

public class J_RingOfTheElements extends ChargedItem {
    public J_RingOfTheElements(Provider provider) {
        super(TicTac7xChargesImprovedConfig.ring_of_the_elements, ItemID.RING_OF_ELEMENTS_CHARGED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.RING_OF_ELEMENTS_CHARGED),
            new TriggerItem(ItemID.RING_OF_ELEMENTS).fixedCharges(0),
        };

        this.triggers.addAll(List.of(
            // Teleport.
            new OnVarbitChanged(VarbitID.RING_OF_THE_ELEMENTS_CHARGES).setDynamically(),

            // Unified menu entry.
            new OnMenuEntryAdded("Rub").replaceOption("Teleport"),

            // Last destination replaced with actual altar.
            new OnMenuEntryAdded("Last Destination").replaceOption("Air Altar").replaceTarget("Ring of the elements", "").varbitCheck(13708, 1),
            new OnMenuEntryAdded("Last Destination").replaceOption("Water Altar").replaceTarget("Ring of the elements", "").varbitCheck(13708, 2),
            new OnMenuEntryAdded("Last Destination").replaceOption("Earth Altar").replaceTarget("Ring of the elements", "").varbitCheck(13708, 3),
            new OnMenuEntryAdded("Last Destination").replaceOption("Fire Altar").replaceTarget("Ring of the elements", "").varbitCheck(13708, 4)
        ));
    }
}