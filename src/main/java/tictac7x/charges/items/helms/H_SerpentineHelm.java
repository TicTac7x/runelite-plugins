package tictac7x.charges.items.helms;

import net.runelite.api.gameval.*;
import net.runelite.api.widgets.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

import java.util.*;

public class H_SerpentineHelm extends ChargedItemWithStorage {
    public H_SerpentineHelm(Provider provider) {
        this(TicTac7xChargesImprovedConfig.serpentine_helm, "Serpentine helm", ItemID.SERPENTINE_HELM_CHARGED, new TriggerItem[]{
            new TriggerItem(ItemID.SERPENTINE_HELM).fixedCharges(0),
            new TriggerItem(ItemID.SERPENTINE_HELM_CHARGED)
        }, provider);
    }

    public H_SerpentineHelm(String configKey, String itemName, int itemId, TriggerItem[] items, Provider provider) {
        super(configKey, itemId, provider);

        this.items = items;

        this.storage.storableItems(
            new StorableItem(ItemID.SNAKEBOSS_SCALE)
        );

        this.triggers.addAll(List.of(
            // Check
            new OnChatMessage("Scales: (?<scales>.+) \\(.*\\)").onItemClick().matcherConsumer(m -> {
                storage.clearAndPut(ItemID.SNAKEBOSS_SCALE, TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("scales")));
            }),

            // Uncharge
            new OnScriptPreFired(1651).scriptConsumer((script) -> {
                Optional<Widget> widget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, 584, 5);
                if (
                    widget.isPresent() &&
                        widget.get().getItemId() == this.itemId &&
                        script.arguments.length >= 5 &&
                        script.arguments[4].toString().equals("Yes")
                ) {
                    provider.store.addConsumerToNextTickQueue(() -> storage.clear());
                }
            }),

            // Degrade in combat - Note that this may always be off by 10 because the moment the player is in combat it consumes 10 scales, and then 10 every 90 ticks
            // But the exact timing is not known for the "grace" period on the initial consumption. Therefore, I won't account for that initial consumption
            new OnCombat(90).isEquipped().consumer(() -> storage.remove(ItemID.SNAKEBOSS_SCALE, 10)),

            // Auto-charge.
            new OnAutoChargeMessage(itemName, "Zulrah's scales", 1, this, ItemID.SNAKEBOSS_SCALE),

            // Ran out of charges upon degrading in combat
            new OnChatMessage("Your serpentine helm has run out of Zulrah's scales.").consumer(() -> storage.clear())
        ));
    }
}
