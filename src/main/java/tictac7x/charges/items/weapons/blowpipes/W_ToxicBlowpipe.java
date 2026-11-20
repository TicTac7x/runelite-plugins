package tictac7x.charges.items.weapons.blowpipes;

import net.runelite.api.ChatMessageType;
import net.runelite.api.gameval.*;
import net.runelite.api.widgets.*;
import net.runelite.client.chat.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class W_ToxicBlowpipe extends ChargedItemWithStorage {
    // Expected (not yet applied) consumption since the last whole unit was removed.
    // Darts are tracked in percent (100 = one dart), scales in thirds (3 = one scale),
    // so the estimate follows the game's average usage instead of rolling its own dice.
    private int dartsOwedPercent = 0;
    private int scalesOwedThirds = 0;

    // The estimate is only known to match the game right after a Check.
    private boolean checkedThisLogin = false;
    private boolean remindedThisLogin = false;

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
                onChecked();
            }),

            // Check with darts.
            new OnChatMessage("Darts: (?<dartstype>.+) x (?<dartsamount>.+)\\. Scales: (?<scales>.+) \\(.*\\).").matcherConsumer(m -> {
                storage.clearAndPut(ItemID.SNAKEBOSS_SCALE, TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("scales")));

                Optional<StorageItem> darts = getStorageItemFromName(m.group("dartstype"), TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("dartsamount")));
                if (darts.isPresent()) {
                    storage.put(darts);
                }
                onChecked();
            }).onItemClick(),

            // Load darts or scales by using them on the blowpipe.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Ran out of scales, darts or both. The game is authoritative here, so sync the estimate to it.
            new OnChatMessage("Your blowpipe has run out of (?<what>scales and darts|scales|darts)\\.").matcherConsumer(m -> {
                if (m.group("what").contains("scales")) {
                    storage.remove(ItemID.SNAKEBOSS_SCALE);
                }
                if (m.group("what").contains("darts")) {
                    removeAllDarts();
                }
                resetOwed();
            }),

            // Unload (empty only darts)
            new OnMenuOptionClicked("Unload").onItemClick().runConsumerOnNextGameTick(() -> {
                storage.clearAndPut(storage.getStorage().getItem(ItemID.SNAKEBOSS_SCALE));
                resetOwed();
            }),

            // Uncharge (empty darts and scales)
            new OnScriptPreFired(1651).scriptConsumer((script) -> {
                Optional<Widget> widget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, 584, 5);
                if (
                    widget.isPresent() && Arrays.stream(items).anyMatch(item -> item.itemId == widget.get().getItemId()) &&
                    script.arguments.length >= 5 &&
                    script.arguments[4].toString().equals("Yes")
                ) {
                    provider.store.addConsumerToNextTickQueue(() -> {
                        storage.clear();
                        resetOwed();
                    });
                }
            }),

            // Attack.
            new OnAnimationChanged(5061).isEquipped().consumer(() -> {
                // 2/3 chance to use a scale.
                scalesOwedThirds += 2;
                if (scalesOwedThirds >= 3) {
                    scalesOwedThirds -= 3;
                    storage.remove(ItemID.SNAKEBOSS_SCALE, 1);
                }

                // Ava's devices save a percentage of darts.
                dartsOwedPercent += 100 - TicTac7xChargesImprovedPlugin.getRangedAmmoRecoveryRate(provider);
                if (dartsOwedPercent >= 100) {
                    dartsOwedPercent -= 100;
                    for (StorageItem item : storage.getStorage().getItems()) {
                        if (item.itemId != ItemID.SNAKEBOSS_SCALE) {
                            storage.remove(item.itemId, 1);
                        }
                    }
                }
            }),

            // Auto-charge.
            new OnAutoChargeMessage("Toxic blowpipe", "Zulrah's scales", 1, this, ItemID.SNAKEBOSS_SCALE)
        ));
    }

    private void resetOwed() {
        dartsOwedPercent = 0;
        scalesOwedThirds = 0;
    }

    private void onChecked() {
        resetOwed();
        checkedThisLogin = true;
    }

    @Override
    public void onLogin() {
        checkedThisLogin = false;
        remindedThisLogin = false;
    }

    @Override
    public void onInventoryOrEquipmentUpdated() {
        if (remindedThisLogin || checkedThisLogin || !provider.config.showBlowpipeCheckReminder()) {
            return;
        }

        // Nothing to be wrong about yet.
        if (getRemainingCharges() == 0) {
            return;
        }

        remindedThisLogin = true;
        provider.chatMessageManager.queue(QueuedMessage.builder()
            .type(ChatMessageType.CONSOLE)
            .runeLiteFormattedMessage("<colHIGHLIGHT>[Item Charges Improved] " + getItemName() + " charges are an estimate since your last login. Check it to sync them with the game.")
            .build()
        );
    }

    private boolean isEstimateMarked() {
        return provider.config.showBlowpipeEstimateMarker() && !checkedThisLogin && getRemainingCharges() > 0;
    }

    private void removeAllDarts() {
        for (StorageItem item : storage.getStorage().getItems()) {
            if (item.itemId != ItemID.SNAKEBOSS_SCALE) {
                storage.remove(item.itemId);
            }
        }
    }

    private int getScales() {
        return getStorage().count(ItemID.SNAKEBOSS_SCALE);
    }

    private int getDarts() {
        int darts = 0;

        for (StorageItem item : getStorage().getItems()) {
            if (item.itemId != ItemID.SNAKEBOSS_SCALE) {
                darts += item.getQuantity();
            }
        }

        return darts;
    }

    /**
     * A shot needs both a dart and a scale, so the blowpipe has only as many
     * charges as the scarcer of the two. A missing dart or scale entry counts as 0,
     * it must never fall back to the count of whatever is left.
     */
    private int getRemainingCharges() {
        return Math.min(getScales(), getDarts());
    }

    @Override
    public String getChargesString(int itemId) {
        return this.getTotalChargesString();
    }

    @Override
    public String getTotalChargesString() {
        String charges = TicTac7xChargesImprovedPlugin.getChargesMinified(getRemainingCharges());
        return isEstimateMarked() ? "~" + charges : charges;
    }

    @Override
    public Color getTextColor(int itemId) {
        return this.getTotalTextColor();
    }

    @Override
    public Color getTotalTextColor() {
        if (getRemainingCharges() == 0) {
            return provider.config.getColorEmpty();
        }

        return super.getTotalTextColor();
    }
}
