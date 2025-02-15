package tictac7x.charges.item;

import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.events.*;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.JagexColors;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.util.ColorUtil;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.listeners.*;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.AdvancedMenuEntry;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Store;

import javax.annotation.Nonnull;
import java.awt.Color;
import java.util.Optional;

public abstract class ChargedItemBase {
    public final String configKey;
    protected final Client client;
    protected final ClientThread clientThread;
    protected final ItemManager itemManager;
    protected final InfoBoxManager infoBoxManager;
    protected final ConfigManager configManager;
    protected final ChatMessageManager chatMessageManager;
    protected final Notifier notifier;
    protected final TicTac7xChargesImprovedConfig config;

    public final Store store;

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

    public boolean inInventory = false;
    public boolean inEquipment = false;

    public ChargedItemBase(
        final String configKey,
        final int itemId,
        final Client client,
        final ClientThread clientThread,
        final ConfigManager configManager,
        final ItemManager itemManager,
        final InfoBoxManager infoBoxManager,
        final ChatMessageManager chatMessageManager,
        final Notifier notifier,
        final TicTac7xChargesImprovedConfig config,
        final Store store
    ) {
        this.itemId = itemId;
        this.configKey = configKey;

        this.client = client;
        this.clientThread = clientThread;
        this.configManager = configManager;
        this.itemManager = itemManager;
        this.infoBoxManager = infoBoxManager;
        this.chatMessageManager = chatMessageManager;
        this.notifier = notifier;
        this.config = config;
        this.store = store;

        listenerOnChatMessage = new ListenerOnChatMessage(client, itemManager, this, notifier, config);
        listenerOnItemContainerChanged = new ListenerOnItemContainerChanged(client, itemManager, this, notifier, config);
        listenerOnItemPickup = new ListenerOnItemPickup(client, itemManager, this, notifier, config);
        listenerOnXpDrop = new ListenerOnXpDrop(client, itemManager, this, notifier, config);
        listenerOnStatChanged = new ListenerOnStatChanged(client, itemManager, this, notifier, config);
        listenerOnMenuEntryAdded = new ListenerOnMenuEntryAdded(client, itemManager, this, notifier, config);
        listenerOnResetDaily = new ListenerOnResetDaily(client, itemManager, this, notifier, config);
        listenerOnGraphicChanged = new ListenerOnGraphicChanged(client, itemManager, this, notifier, config);
        listenerOnAnimationChanged = new ListenerOnAnimationChanged(client, itemManager, this, notifier, config);
        listenerOnHitsplatApplied = new ListenerOnHitsplatApplied(client, itemManager, this, notifier, config);
        listenerOnWidgetLoaded = new ListenerOnWidgetLoaded(client, itemManager, this, notifier, config);
        listenerOnVarbitChanged = new ListenerOnVarbitChanged(client, itemManager, this, notifier, config);
        listenerOnUserAction = new ListenerOnUserAction(client, itemManager, this, notifier, config);
        listenerOnMenuOptionClicked = new ListenerOnMenuOptionClicked(client, itemManager, this, notifier, config);
        listenerOnScriptPreFired = new ListenerOnScriptPreFired(client, itemManager, this, notifier, config);
    }

    public abstract String getCharges();

    public abstract String getCharges(final int itemId);

    public abstract String getTotalCharges();

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
        return getItemName() + (needsToBeEquipped() && !inEquipment() ? " (needs to be equipped)" : "") + ": " + ColorUtil.wrapWithColorTag(String.valueOf(getCharges()), JagexColors.MENU_TARGET);
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
        return itemManager.getItemComposition(itemId).getName();
    }

    public boolean needsToBeEquipped() {
        return getCurrentItem().needsToBeEquipped.isPresent();
    }

    public Color getTextColor() {
        if (getCharges().equals("?")) {
            return config.getColorUnknown();
        }

        if (getCharges().equals("0") || needsToBeEquipped() && !inEquipment()) {
            return config.getColorEmpty();
        }

        return config.getColorDefault();
    }

    public Color getTextColor(final int itemId) {
        for (final TriggerItem triggerItem : items) {
            if (triggerItem.itemId == itemId && triggerItem.fixedCharges.isPresent() && triggerItem.fixedCharges.get() == 0) {
                return config.getColorEmpty();
            }
        }

        return getTextColor();
    }

    protected String getChargesMinified(final int charges) {
        // Unlimited.
        if (charges == Charges.UNLIMITED) return "∞";

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

    public void onChatMessage(final ChatMessage event) {
        if (!inInventoryOrEquipment()) return;
        listenerOnChatMessage.trigger(event);
    }

    public void onHitsplatApplied(final HitsplatApplied event) {
        if (!inInventoryOrEquipment()) return;
        listenerOnHitsplatApplied.trigger(event);
    }

    public void onWidgetLoaded(final WidgetLoaded event) {
        if (!inInventoryOrEquipment()) return;
        clientThread.invokeLater(() -> {
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

    public void onItemContainerChanged(final ItemContainerChanged event) {
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

    public void onMenuOptionClicked(final AdvancedMenuEntry event) {
        if (!inInventoryOrEquipment()) return;
        listenerOnMenuOptionClicked.trigger(event);
    }

    public void onScriptPreFired(final ScriptPreFired event) {
        if (!inInventoryOrEquipment()) return;
        listenerOnScriptPreFired.trigger(event);
    }
}
