package tictac7x.charges.items.jewelry;

import net.runelite.api.widgets.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class J_RingOfEndurance extends ChargedItem {
    public J_RingOfEndurance(Provider provider) {
        super(TicTac7xChargesImprovedConfig.ring_of_endurance, ItemId.RING_OF_ENDURANCE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.RING_OF_ENDURANCE),
            new TriggerItem(ItemId.RING_OF_ENDURANCE_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.RING_OF_ENDURANCE_NOCHARGES).fixedCharges(0),
        };

        this.triggers.addAll(List.of(
            // Charge.
            new OnChatMessage("You load your Ring of endurance with (?<charges>.+) stamina doses?.").increaseDynamically(),

            // Check.
            new OnChatMessage("Your Ring of endurance is charged with (?<charges>.+) stamina doses?.").setDynamicallyCharges(),

            // Use charge.
            new OnChatMessage("Your Ring of endurance doubles the duration of your stamina potion's effect.").decreaseCharges(1),

            // Uncharge.
            new OnMenuOptionClicked("Yes").runConsumerOnNextGameTick(() -> {
                Optional<Widget> unchargeWidget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, 584, 0, 2);
                if (unchargeWidget.isPresent() && unchargeWidget.get().getText().equals("Are you sure you want to uncharge your Ring of endurance?")) {
                    setCharges(0);
                }
            })
        ));
    }
}