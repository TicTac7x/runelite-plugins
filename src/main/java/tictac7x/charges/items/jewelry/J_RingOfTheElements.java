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
            new OnMenuEntryAdded("Last Destination").replaceOptionConsumer(() -> {
                switch (provider.client.getVarbitValue(VarbitID.RING_OF_ELEMENTS_LAST_DESTINATION)) {
                    case 1:
                        return "Air Altar";
                    case 2:
                        return "Water Altar";
                    case 3:
                        return "Earth Altar";
                    case 4:
                        return "Fire Altar";
                    default:
                        return "Last Destination";
                }
            })
        ));
    }
}