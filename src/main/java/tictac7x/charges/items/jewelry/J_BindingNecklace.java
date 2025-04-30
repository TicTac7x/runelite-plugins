package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import net.runelite.api.widgets.Widget;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

import java.util.Optional;

public class J_BindingNecklace extends ChargedItem {
    public J_BindingNecklace(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.binding_necklace, ItemId.BINDING_NECKLACE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BINDING_NECKLACE).needsToBeEquipped(),
        };

        this.triggers = new TriggerBase[] {
            // Check, one left.
            new OnChatMessage("You have one charge left before your Binding necklace disintegrates.").setFixedCharges(1),

            // Check.
            new OnChatMessage("You have (?<charges>.+) charges left before your Binding necklace disintegrates.").setDynamicallyCharges(),

            // Charge used.
            new OnChatMessage("You (partially succeed to )?bind the temple's power into (mud|lava|steam|dust|smoke|mist) runes\\.").decreaseCharges(1),

            // Fully used.
            new OnChatMessage("Your Binding necklace has disintegrated.").runConsumerOnNextGameTick(() -> setCharges(16)),

            // Destroy.
            new OnScriptPreFired(1651).scriptConsumer((script) -> {
                final Optional<Widget> destroyWidget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, 584, 0, 2);
                if (
                    destroyWidget.isPresent() && destroyWidget.get().getText().equals("Destroy necklace of binding?") &&
                    script.getScriptEvent().getArguments().length >= 5 &&
                    script.getScriptEvent().getArguments()[4].toString().equals("Yes")
                ) {
                    provider.store.addConsumerToNextTickQueue(() -> setCharges(16));
                }
            }),
        };
    }
}
