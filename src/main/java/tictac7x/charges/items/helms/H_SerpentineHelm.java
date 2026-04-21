package tictac7x.charges.items.helms;

import java.util.List;
import java.util.Optional;
import net.runelite.api.widgets.Widget;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnCombat;
import tictac7x.charges.item.triggers.OnScriptPreFired;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class H_SerpentineHelm extends ChargedItemWithStorage {
    public H_SerpentineHelm(final Provider provider) {
        this(TicTac7xChargesImprovedConfig.serpentine_helm, ItemId.SERPENTINE_HELM, new TriggerItem[]{
            new TriggerItem(ItemId.SERPENTINE_HELM_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.SERPENTINE_HELM)
        }, provider);
    }

    public H_SerpentineHelm(final String configKey, final int itemId, final TriggerItem[] items, final Provider provider) {
        super(configKey, itemId, provider);

        this.items = items;

        this.storage.storableItems(
            new StorableItem(ItemId.ZULRAH_SCALES)
        );

        this.triggers.addAll(List.of(
            // Check
            new OnChatMessage("Scales: (?<scales>.+) \\(.*\\)").onItemClick().matcherConsumer(m -> {
                final StorageItem scales = new StorageItem(ItemId.ZULRAH_SCALES, TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("scales")));
                storage.clearAndPut(scales);
            }),

            // Uncharge
            new OnScriptPreFired(1651).scriptConsumer((script) -> {
                final Optional<Widget> widget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, 584, 5);
                if (
                    widget.isPresent() &&
                        widget.get().getItemId() == this.itemId &&
                        script.getScriptEvent().getArguments().length >= 5 &&
                        script.getScriptEvent().getArguments()[4].toString().equals("Yes")
                ) {
                    provider.store.addConsumerToNextTickQueue(() -> storage.clear());
                }
            }),

            // Degrade in combat - Note that this may always be off by 10 because the moment the player is in combat it consumes 10 scales, and then 10 every 90 ticks
            // But the exact timing is not known for the "grace" period on the initial consumption. Therefore, I won't account for that initial consumption
            new OnCombat(90).isEquipped().consumer(() -> storage.remove(ItemId.ZULRAH_SCALES, 10)),

            // Ran out of charges upon degrading in combat
            new OnChatMessage("Your serpentine helm has run out of Zulrah's scales.").consumer(() -> storage.clear())
        ));
    }
}
