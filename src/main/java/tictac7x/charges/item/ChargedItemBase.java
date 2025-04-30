package tictac7x.charges.item;

import net.runelite.api.events.*;
import net.runelite.client.ui.JagexColors;
import net.runelite.client.util.ColorUtil;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.customEvents.CustomChatMessage;
import tictac7x.charges.customEvents.CustomHitsplatApplied;
import tictac7x.charges.item.listeners.*;
import tictac7x.charges.customEvents.CustomItemContainerChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.customEvents.CustomMenuOptionClicked;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Provider;

import javax.annotation.Nonnull;
import java.awt.Color;

import static tictac7x.charges.TicTac7xChargesImprovedPlugin.INFINITE_SYMBOL;

public abstract class ChargedItemBase {
    public final Provider provider;

    final String configKey;
    public int itemId;

    public TriggerItem[] items = new TriggerItem[]{};
    public TriggerBase[] triggers = new TriggerBase[]{};

    private final ListenerOnChatMessage listenerOnChatMessage;
    private final ListenerOnItemContainerChanged listenerOnItemContainerChanged;
    private final ListenerOnItemPickup listenerOnItemPickup;
    private final ListenerOnXpDrop listenerOnXpDrop;
    private final ListenerOnStatChanged listenerOnStatChanged;
    private final ListenerOnMenuEntryAdded listenerOnMenuEntryAdded;
    private final ListenerOnResetDaily listenerOnResetDaily;
    private final ListenerOnGraphicChanged listenerOnGraphicChanged;
    private final ListenerOnAnimationChanged listenerOnAnimationChanged;
    private final ListenerOnHitsplatApplied listenerOnHitsplatApplied;
    private final ListenerOnWidgetLoaded listenerOnWidgetLoaded;
    private final ListenerOnVarbitChanged listenerOnVarbitChanged;
    private final ListenerOnUserAction listenerOnUserAction;
    private final ListenerOnMenuOptionClicked listenerOnMenuOptionClicked;
    private final ListenerOnScriptPreFired listenerOnScriptPreFired;
    private final ListenerOnCombat listenerOnCombat;
    private final ListenerOnGameTick listenerOnGameTick;

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

        listenerOnChatMessage = new ListenerOnChatMessage(provider, this);
        listenerOnItemContainerChanged = new ListenerOnItemContainerChanged(provider, this);
        listenerOnItemPickup = new ListenerOnItemPickup(provider, this);
        listenerOnXpDrop = new ListenerOnXpDrop(provider, this);
        listenerOnStatChanged = new ListenerOnStatChanged(provider, this);
        listenerOnMenuEntryAdded = new ListenerOnMenuEntryAdded(provider, this);
        listenerOnResetDaily = new ListenerOnResetDaily(provider, this);
        listenerOnGraphicChanged = new ListenerOnGraphicChanged(provider, this);
        listenerOnAnimationChanged = new ListenerOnAnimationChanged(provider, this);
        listenerOnHitsplatApplied = new ListenerOnHitsplatApplied(provider, this);
        listenerOnWidgetLoaded = new ListenerOnWidgetLoaded(provider, this);
        listenerOnVarbitChanged = new ListenerOnVarbitChanged(provider, this);
        listenerOnUserAction = new ListenerOnUserAction(provider, this);
        listenerOnMenuOptionClicked = new ListenerOnMenuOptionClicked(provider, this);
        listenerOnScriptPreFired = new ListenerOnScriptPreFired(provider, this);
        listenerOnCombat = new ListenerOnCombat(provider, this);
        listenerOnGameTick = new ListenerOnGameTick(provider, this);
    }

    public abstract String getCharges(final int itemId);

    public abstract String getTotalCharges();

    public String getConfigKey() {
        return (
            configKey.startsWith(TicTac7xChargesImprovedConfig.potion_) ? TicTac7xChargesImprovedConfig.potion_ :
            configKey.startsWith(TicTac7xChargesImprovedConfig.barrows_gear_) ? TicTac7xChargesImprovedConfig.barrows_gear_ :
            configKey.startsWith(TicTac7xChargesImprovedConfig.moons_gear_) ? TicTac7xChargesImprovedConfig.moons_gear_ :
            configKey
        ).replaceAll("_$", "");
    }

    public boolean inInventory() {
        return inInventory;
    }

    public boolean inEquipment() {
        return inEquipment;
    }

    private boolean inInventoryOrEquipment() {
        return inInventory || inEquipment;
    }

    public String getTooltip() {
        return getItemName() + (needsToBeEquipped() && !inEquipment() ? " (needs to be equipped)" : "") + ": " + ColorUtil.wrapWithColorTag(String.valueOf(getCharges(itemId)), JagexColors.MENU_TARGET);
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

    public Color getTextColor() {
        if (getCharges(itemId).equals("?")) {
            return provider.config.getColorUnknown();
        }

        if (getCharges(itemId).equals("0") || needsToBeEquipped() && !inEquipment()) {
            return provider.config.getColorEmpty();
        }

        return provider.config.getColorDefault();
    }

    public Color getTextColor(final int itemId) {
        for (final TriggerItem triggerItem : items) {
            if (triggerItem.itemId == itemId && triggerItem.fixedCharges.isPresent() && triggerItem.fixedCharges.get() == 0) {
                return provider.config.getColorEmpty();
            }
        }

        return getTextColor();
    }

    protected String getChargesMinified(final int charges) {
        // Unlimited.
        if (charges == Charges.UNLIMITED) return INFINITE_SYMBOL;

        // Unknown.
        if (charges == Charges.UNKNOWN) return "?";

        // Show as is.
        if (charges < 1000) return String.valueOf(charges);

        // Minify to use millions (M).
        if (charges >= 1000000) return charges / 1000000 + "M";

        // Minify to use thousands (K).
        final int thousands = charges / 1000;
        final int hundreds = Math.min((charges % 1000 + 50) / 100, 9);
        return thousands + (thousands < 10 && hundreds > 0 ? "." + hundreds : "") + "K";
    }

    public void onChatMessage(final CustomChatMessage event) {
        if (event.message.contains("The banker charges") || inInventoryOrEquipment()) {
            listenerOnChatMessage.trigger(event);
        }
    }

    public void onHitsplatApplied(final CustomHitsplatApplied event) {
        if (!inInventoryOrEquipment()) return;
        listenerOnHitsplatApplied.trigger(event);
    }

    public void onWidgetLoaded(final WidgetLoaded event) {
        if (!inInventoryOrEquipment()) return;
        provider.clientThread.invokeLater(() -> {
            listenerOnWidgetLoaded.trigger(event);
        });
    }

    public void onVarbitChanged(final VarbitChanged event) {
        if (!inInventoryOrEquipment()) return;
        listenerOnVarbitChanged.trigger(event);
    }

    public void onStatChanged(final StatChanged event) {
        listenerOnStatChanged.trigger(event);
        if (!inInventoryOrEquipment()) return;
        listenerOnXpDrop.trigger(event);
    }

    public void onGraphicChanged(final GraphicChanged event) {
        if (!inInventoryOrEquipment()) return;
        listenerOnGraphicChanged.trigger(event);
    }

    public void onAnimationChanged(final AnimationChanged event) {
        if (!inInventoryOrEquipment()) return;
        listenerOnAnimationChanged.trigger(event);
    }

    public void onItemContainerChanged(final CustomItemContainerChanged event) {
        if (!inInventoryOrEquipment()) return;
        listenerOnItemContainerChanged.trigger(event);
    }

    public void onMenuEntryAdded(final MenuEntryAdded event) {
        if (!inInventoryOrEquipment()) return;
        listenerOnMenuEntryAdded.trigger(event);
    }

    public void onItemDespawned(final ItemDespawned event) {
        if (!inInventoryOrEquipment()) return;
        listenerOnItemPickup.trigger(event);
    }

    public void onResetDaily() {
        listenerOnResetDaily.trigger();
    }

    public void onUserAction() {
        if (!inInventoryOrEquipment()) return;
        listenerOnUserAction.trigger();
    }

    public void onMenuOptionClicked(final CustomMenuOptionClicked event) {
        if (!inInventoryOrEquipment()) return;
        listenerOnMenuOptionClicked.trigger(event);
    }

    public void onScriptPreFired(final ScriptPreFired event) {
        if (!inInventoryOrEquipment()) return;
        listenerOnScriptPreFired.trigger(event);
    }

    public void onCombat() {
        if (!inInventoryOrEquipment()) return;
        listenerOnCombat.trigger();
    }

    public void onGameTick(final GameTick event) {
        if (!inInventoryOrEquipment()) return;
        listenerOnGameTick.trigger(event);
    }
}
