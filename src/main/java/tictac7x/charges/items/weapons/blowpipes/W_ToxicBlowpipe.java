package tictac7x.charges.items.weapons.blowpipes;

import net.runelite.api.gameval.*;
import net.runelite.api.widgets.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

public class W_ToxicBlowpipe extends ChargedItemWithStorage {
    public W_ToxicBlowpipe(Provider provider) {
        this(provider, TicTac7xChargesImprovedConfig.toxic_blowpipe, ItemID.TOXIC_BLOWPIPE_LOADED, new TriggerItem[]{
            new TriggerItem(ItemID.TOXIC_BLOWPIPE),
            new TriggerItem(ItemID.TOXIC_BLOWPIPE_LOADED),
        });
    }
    public W_ToxicBlowpipe(Provider provider, String configKey, int itemId, TriggerItem[] items) {
        super(configKey, itemId, provider);

        this.items = items;

        this.storage.storableItems(
            new StorableItem(ItemID.SNAKEBOSS_SCALE),
            new StorableItem(ItemID.BRONZE_DART).checkName("Bronze dart"),
            new StorableItem(ItemID.IRON_DART).checkName("Iron dart"),
            new StorableItem(ItemID.STEEL_DART).checkName("Steel dart"),
            new StorableItem(ItemID.MITHRIL_DART).checkName("Mithril dart"),
            new StorableItem(ItemID.ADAMANT_DART).checkName("Adamant dart"),
            new StorableItem(ItemID.RUNE_DART).checkName("Rune dart"),
            new StorableItem(ItemID.AMETHYST_DART).checkName("Amethyst dart"),
            new StorableItem(ItemID.DRAGON_DART).checkName("Dragon dart")
        );

        this.triggers.addAll(List.of(
            // Check without darts.
            new OnChatMessage("Darts: None\\. Scales: (?<scales>.+) \\(.*\\).").matcherConsumer(m -> {
                StorageItem scales = new StorageItem(ItemID.SNAKEBOSS_SCALE, TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("scales")));
                storage.clearAndPut(scales);

            }),

            // Check with darts.
            new OnChatMessage("Darts: (?<dartstype>.+) x (?<dartsamount>.+)\\. Scales: (?<scales>.+) \\(.*\\).").matcherConsumer(m -> {
                storage.clearAndPut(ItemID.SNAKEBOSS_SCALE, TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("scales")));

                Optional<StorageItem> darts = getStorageItemFromName(m.group("dartstype"), TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("dartsamount")));
                if (darts.isPresent()) {
                    storage.put(darts);
                }
            }).onItemClick(),

            // Unload (empty only darts)
            new OnMenuOptionClicked("Unload").onItemClick().runConsumerOnNextGameTick(() -> {
                storage.clearAndPut(storage.getStorage().getItem(ItemID.SNAKEBOSS_SCALE));
            }),

            // Uncharge (empty darts and scales)
            new OnScriptPreFired(1651).scriptConsumer((script) -> {
                Optional<Widget> widget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, 584, 5);
                if (
                    widget.isPresent() && Arrays.stream(items).anyMatch(item -> item.itemId == widget.get().getItemId()) &&
                    script.arguments.length >= 5 &&
                    script.arguments[4].toString().equals("Yes")
                ) {
                    provider.store.addConsumerToNextTickQueue(() -> storage.clear());
                }
            }),

            // Attack.
            new OnAnimationChanged(5061).isEquipped().consumer(() -> {
                // 1/3 chance to not use scale.
                if (ThreadLocalRandom.current().nextInt(1, 4) != 3) {
                    storage.remove(ItemID.SNAKEBOSS_SCALE, 1);
                }

                // Calculate if dart could have been used.
                for (StorageItem item : storage.getStorage().getItems()) {
                    if (item.itemId != ItemID.SNAKEBOSS_SCALE && TicTac7xChargesImprovedPlugin.guessIfRangedAmmoRetrievalWasSuccessful(provider)) {
                        storage.remove(item.itemId, 1);
                    }
                }
            })
        ));
    }

    @Override
    public String getChargesString(int itemId) {
        return this.getTotalChargesString();
    }

    @Override
    public String getTotalChargesString() {
        Optional<Integer> charges = Optional.empty();

        for (StorageItem item : getStorage().getItems()) {
            if (!charges.isPresent()) {
                charges = Optional.of(item.getQuantity());
            } else if (item.getQuantity() < charges.get()) {
                charges = Optional.of(item.getQuantity());
            }
        }

        return TicTac7xChargesImprovedPlugin.getChargesMinified(charges.orElse(0));
    }

    @Override
    public Color getTextColor(int itemId) {
        return this.getTotalTextColor();
    }

    @Override
    public Color getTotalTextColor() {
        if (storage.getStorage().getItems().size() != 2) {
            return provider.config.getColorEmpty();
        }

        return super.getTotalTextColor();
    }
}
