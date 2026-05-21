package tictac7x.charges.item;

import net.runelite.api.events.*;
import net.runelite.client.ui.JagexColors;
import net.runelite.client.util.ColorUtil;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.events.*;
import tictac7x.charges.item.listeners.*;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ChargeId;

import javax.annotation.Nonnull;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ChargedItemBase {
    public final Provider provider;

    final String configKey;
    public int itemId;

    public TriggerItem[] items = new TriggerItem[]{};
    public final List<TriggerBase> triggers = new ArrayList<>();



    public boolean inInventory = false;
    public boolean inEquipment = false;

    public ChargedItemBase(
        final String configKey,
        final int itemId,
        final Provider provider
    ) {
        this.provider = provider;

        this.itemId = itemId;
        this.configKey = configKey;
    }

    public abstract int getCharges(final int itemId);

    public abstract int getTotalCharges();

    public String getChargesString(final int itemId) {
        return TicTac7xChargesImprovedPlugin.getChargesMinified(getCharges(itemId));
    }

    public String getLongChargesString(final int itemId) {
        final int charges = getCharges(itemId);

        // Unlimited.
        if (charges == ChargeId.UNLIMITED) return TicTac7xChargesImprovedPlugin.INFINITE_SYMBOL;

        // Unknown.
        if (charges == ChargeId.UNKNOWN) return "?";

        return String.valueOf(charges);
    }

    public String getTotalChargesString() {
        return TicTac7xChargesImprovedPlugin.getChargesMinified(getTotalCharges());
    }

    public String getConfigKey() {
        return configKey;
    }

    public String getInfoboxName() {
        return configKey;
    }

    public boolean inInventory() {
        return inInventory;
    }

    public boolean inEquipment() {
        return inEquipment;
    }

    public boolean inInventoryOrEquipment() {
        return inInventory() || inEquipment();
    }

    public String getTooltip() {
        return getItemName() + (needsToBeEquipped() && !inEquipment() ? " (needs to be equipped)" : "") + ": " + ColorUtil.wrapWithColorTag(getLongChargesString(itemId), JagexColors.MENU_TARGET);
    }

    @Nonnull
    private TriggerItem getCurrentItem() {
        for (final TriggerItem triggerItem : items) {
            if (triggerItem.itemId == itemId) {
                return triggerItem;
            }
        }

        return null;
    }

    public String getItemName() {
        return provider.itemManager.getItemComposition(itemId).getName();
    }

    public boolean needsToBeEquipped() {
        return getCurrentItem().needsToBeEquipped.isPresent();
    }

    public Optional<Integer> getMaxCharges() {
        for (final TriggerItem item : items) {
            if (item.itemId == itemId && item.maxCharges.isPresent()) {
                return item.maxCharges;
            }
        }

        return Optional.empty();
    }

    private Color getColorForCharges(final int charges) {
        if (charges == ChargeId.UNKNOWN) {
            return provider.config.getColorUnknown();
        }

        if (charges == 0) {
            return provider.config.getColorEmpty();
        }

        if (needsToBeEquipped() && inEquipment()) {
            return provider.config.getColorActivated();
        }

        if (needsToBeEquipped() && !inEquipment()) {
            return provider.config.getColorEmpty();
        }

        return provider.config.getColorDefault();
    }

    public Color getTotalTextColor() {
        return getColorForCharges(getTotalCharges());
    }

    public Color getTextColor(final int itemId) {
        return getColorForCharges(getCharges(itemId));
    }
}
