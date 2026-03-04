package tictac7x.charges.items.weapons;

import net.runelite.api.widgets.Widget;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnAnimationChanged;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnMenuOptionClicked;
import tictac7x.charges.item.triggers.OnScriptPreFired;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

import java.awt.Color;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class W_ToxicBlowpipe extends ChargedItemWithStorage {
    public W_ToxicBlowpipe(final Provider provider) {
        this(TicTac7xChargesImprovedConfig.toxic_blowpipe, ItemId.TOXIC_BLOWPIPE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TOXIC_BLOWPIPE_UNCHARGED),
            new TriggerItem(ItemId.TOXIC_BLOWPIPE),
        };
    }
    public W_ToxicBlowpipe(final String configKey, final int itemId, final Provider provider) {
        super(configKey, itemId, provider);

        this.storage.storableItems(
            new StorableItem(ItemId.ZULRAH_SCALES),
            new StorableItem(ItemId.BRONZE_DART).checkName("Bronze dart"),
            new StorableItem(ItemId.IRON_DART).checkName("Iron dart"),
            new StorableItem(ItemId.STEEL_DART).checkName("Steel dart"),
            new StorableItem(ItemId.MITHRIL_DART).checkName("Mithril dart"),
            new StorableItem(ItemId.ADAMANT_DART).checkName("Adamant dart"),
            new StorableItem(ItemId.RUNE_DART).checkName("Rune dart"),
            new StorableItem(ItemId.AMETHYST_DART).checkName("Amethyst dart"),
            new StorableItem(ItemId.DRAGON_DART).checkName("Dragon dart")
        );

        this.triggers.addAll(List.of(
            // Check without darts.
            new OnChatMessage("Darts: None\\. Scales: (?<scales>.+) \\(.*\\).").matcherConsumer(m -> {
                final StorageItem scales = new StorageItem(ItemId.ZULRAH_SCALES, TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("scales")));
                storage.clearAndPut(scales);

            }),

            // Check with darts.
            new OnChatMessage("Darts: (?<dartstype>.+) x (?<dartsamount>.+)\\. Scales: (?<scales>.+) \\(.*\\).").matcherConsumer(m -> {
                final StorageItem scales = new StorageItem(ItemId.ZULRAH_SCALES, TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("scales")));
                storage.put(scales);

                final Optional<StorageItem> darts = getStorageItemFromName(m.group("dartstype"), TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("dartsamount")));
                if (darts.isPresent()) {
                    storage.put(darts);
                }
            }),

            // Unload (empty only darts)
            new OnMenuOptionClicked("Unload").onItemClick().runConsumerOnNextGameTick(() -> {
                storage.clearAndPut(storage.getStorage().getItem(ItemId.ZULRAH_SCALES));
            }),

            // Uncharge (empty darts and scales)
            new OnScriptPreFired(1651).scriptConsumer((script) -> {
                final Optional<Widget> widget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, 584, 5);
                if (
                    widget.isPresent() &&
                    widget.get().getItemId() == ItemId.TOXIC_BLOWPIPE &&
                    script.getScriptEvent().getArguments().length >= 5 &&
                    script.getScriptEvent().getArguments()[4].toString().equals("Yes")
                ) {
                    provider.store.addConsumerToNextTickQueue(() -> storage.clear());
                }
            }),

            // Attack.
            new OnAnimationChanged(5061).isEquipped().consumer(() -> {
                // 1/3 chance to not use scale.
                if (ThreadLocalRandom.current().nextInt(1, 4) != 3) {
                    storage.remove(ItemId.ZULRAH_SCALES, 1);
                }

                // Determine the dart recovery rate based on the equipped item.
                int recoveryRate = 0;
                if (provider.store.equipmentContainsItem(ItemId.AVAS_ATTRACTOR)) {
                    recoveryRate = 60;
                } else if (provider.store.equipmentContainsItem(ItemId.AVAS_ACCUMULATOR)) {
                    recoveryRate = 72;
                } else if (provider.store.equipmentContainsItem(
                    ItemId.AVAS_ASSEMBLER,
                    ItemId.AVAS_ASSEMBLER_TROUVER,
                    ItemId.AVAS_ASSEMBLER_MASORI,
                    ItemId.AVAS_ASSEMBLER_MASORI_TROUVER,
                    ItemId.AVAS_ASSEMBLER_MAX_SKILLCAPE,
                    ItemId.AVAS_ASSEMBLER_MAX_SKILLCAPE_TROUVER,
                    ItemId.AVAS_ASSEMBLER_MAX_SKILLCAPE_MASORI,
                    ItemId.AVAS_ASSEMBLER_MAX_SKILLCAPE_MASORI_TROUVER
                )) {
                    recoveryRate = 80;
                }

                // Calculate if dart could have been used.
                for (final StorageItem item : storage.getStorage().getItems()) {
                    if (item.getId() != ItemId.ZULRAH_SCALES && ThreadLocalRandom.current().nextInt(1, 101) > recoveryRate) {
                        storage.remove(item.getId(), 1);
                    }
                }
            })
        ));
    }

    @Override
    public String getChargesString(final int itemId) {
        return this.getTotalChargesString();
    }

    @Override
    public String getTotalChargesString() {
        Optional<Integer> charges = Optional.empty();

        for (final StorageItem item : getStorage().getItems()) {
            if (!charges.isPresent()) {
                charges = Optional.of(item.getQuantity());
            } else if (item.getQuantity() < charges.get()) {
                charges = Optional.of(item.getQuantity());
            }
        }

        return TicTac7xChargesImprovedPlugin.getChargesMinified(charges.orElse(0));
    }

    @Override
    public Color getTextColor(final int itemId) {
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
