package tictac7x.charges.items.jewelry;

import net.runelite.api.widgets.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class J_BindingNecklace extends ChargedItem {
    public J_BindingNecklace(Provider provider) {
        super(TicTac7xChargesImprovedConfig.binding_necklace, ItemId.BINDING_NECKLACE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BINDING_NECKLACE).needsToBeEquipped(),
        };

        this.triggers.addAll(List.of(
            // Check, one left.
            new OnChatMessage("You have one charge left before your Binding necklace disintegrates.").setFixedCharges(1),

            // Check.
            new OnChatMessage("You have (?<charges>.+) charges left before your Binding necklace disintegrates.").setDynamicallyCharges(),

            // Charge used.
            new OnChatMessage("You (partially succeed to )?bind the temple's power into (Mud|Lava|Steam|Dust|Smoke|Mist|Aether) runes?.").decreaseCharges(1),

            // Fully used.
            new OnChatMessage("Your Binding necklace has disintegrated.").runConsumerOnNextGameTick(() -> setCharges(16)),

            // Destroy.
            new OnScriptPreFired(1651).scriptConsumer((script) -> {
                Optional<Widget> destroyWidget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, 584, 0, 2);
                if (
                    destroyWidget.isPresent() && destroyWidget.get().getText().equals("Destroy necklace of binding?") &&
                    script.arguments.length >= 5 &&
                    script.arguments[4].toString().equals("Yes")
                ) {
                    provider.store.addConsumerToNextTickQueue(() -> setCharges(16));
                }
            })
        ));
    }
}
