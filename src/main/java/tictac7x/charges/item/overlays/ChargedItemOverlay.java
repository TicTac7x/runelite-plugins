package tictac7x.charges.item.overlays;

import net.runelite.api.Client;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.ui.overlay.components.TextComponent;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.ItemOverlayLocation;

import java.awt.*;
import java.util.Optional;

public class ChargedItemOverlay extends WidgetItemOverlay {
    private final Client client;
    private final TooltipManager tooltipManager;
    private final ConfigManager configManager;
    private final TicTac7xChargesImprovedConfig config;
    private final ChargedItemBase[] chargedItems;

    public ChargedItemOverlay(
        final Client client,
        final TooltipManager tooltipManager,
        final ItemManager itemManager,
        final ConfigManager configManager,
        final TicTac7xChargesImprovedConfig config,
        final ChargedItemBase[] chargedItems
    ) {
        this.client = client;
        this.tooltipManager = tooltipManager;
        this.configManager = configManager;
        this.config = config;
        this.chargedItems = chargedItems;
        showOnInventory();
        showOnEquipment();
        showOnInterfaces(84);
        showOnBank();
    }

    private boolean isBankWidget(final WidgetItem item_widget) {
        return
            item_widget.getWidget().getParentId() == 786442 ||
            item_widget.getWidget().getParentId() == 786443 ||
            item_widget.getWidget().getParentId() == 786444 ||
            item_widget.getWidget().getParentId() == 786445
        ;
    }


    @Override
    public void renderItemOverlay(final Graphics2D graphics, final int itemId, final WidgetItem widgetItem) {
        if (!config.showOverlays()) return;

        Optional<ChargedItemBase> chargedItem = Optional.empty();
        Optional<TriggerItem> triggerItem = Optional.empty();

        // Find correct charged item.
        chargedItemFinder: for (final ChargedItemBase chargedItemBase : chargedItems) {
            for (final TriggerItem chargedItemTriggerItem : chargedItemBase.items) {
                if (chargedItemTriggerItem.itemId == itemId) {
                    chargedItem = Optional.of(chargedItemBase);
                    triggerItem = Optional.of(chargedItemTriggerItem);
                    break chargedItemFinder;
                }
            }
        }

        // Invalid item.
        if (!chargedItem.isPresent() || !triggerItem.isPresent()) return;

        if (
            // Item overlay disabled.
            !isChargedItemOverlayEnabled(chargedItem.get()) ||

            // Infinity charges hidden.
            !config.showUnlimited() && chargedItem.get().getCharges().equals("∞") ||
            !config.showUnlimited() && triggerItem.get().fixedCharges.isPresent() && triggerItem.get().fixedCharges.get().equals(Charges.UNLIMITED) ||

            // Hide overlays in bank.
            !config.showBankOverlays() && isBankWidget(widgetItem) ||

            // Show overlays only in bank.
            config.showOverlaysOnlyInBank() && client.getWidget(12, 1) == null
        ) return;

        // Get default charges from charged item.
        String charges = chargedItem.get().getCharges();
        Color color = chargedItem.get().getTextColor();

        // Override charges and color for fixed items.
        for (final TriggerItem item : chargedItem.get().items) {
            if (item.itemId == itemId && item.fixedCharges.isPresent()) {
                charges = item.fixedCharges.get() == Charges.UNLIMITED
                    ? "∞"
                    : String.valueOf(item.fixedCharges.get());
                color = item.fixedCharges.get() == 0 ? config.getColorEmpty() : config.getColorDefault();
            }
        }

        graphics.setFont(FontManager.getRunescapeSmallFont());

        final Rectangle bounds = widgetItem.getCanvasBounds();
        final TextComponent charges_component = new TextComponent();
        charges_component.setText(charges);
        final Dimension textDimension = charges_component.render(graphics);

        final int itemOverlayX = (int) ((
            config.itemOverlayLocation() == ItemOverlayLocation.BOTTOM_LEFT ||
            config.itemOverlayLocation() == ItemOverlayLocation.TOP_LEFT
        )
            ? bounds.getMinX()
            : bounds.getMaxX() - textDimension.getWidth() - 5
        );

        final int itemOverlayY = (int) ((
            config.itemOverlayLocation() == ItemOverlayLocation.TOP_LEFT ||
            config.itemOverlayLocation() == ItemOverlayLocation.TOP_RIGHT
        )
            ? bounds.getMinY() + textDimension.getHeight() - 2
            : bounds.getMaxY()
        );

        charges_component.setPosition(new Point(itemOverlayX, itemOverlayY));

        // Set color.
        charges_component.setColor(color);

        // Override for bank items.
        if (isBankWidget(widgetItem) && !chargedItem.get().getCharges().equals("?")) {
            charges_component.setColor(config.getColorDefault());
        }

        charges_component.render(graphics);

        // Charged item with storage
        renderTooltip(chargedItem.get(), widgetItem);
    }

    private void renderTooltip(final ChargedItemBase chargedItem, final WidgetItem widgetItem) {
        // Config, not storage item, empty storage checks.
        if (
            !config.showStorageTooltips() ||
            !(chargedItem instanceof ChargedItemWithStorage)
        ) return;

        // Mouse position check.
        final net.runelite.api.Point mousePosition = client.getMouseCanvasPosition();
        if (!widgetItem.getCanvasBounds().contains(mousePosition.getX(), mousePosition.getY())) return;


        final String tooltip = chargedItem.getTooltip();
        if (!tooltip.isEmpty()) {
            tooltipManager.addFront(new Tooltip(tooltip));
        }
    }

    private boolean isChargedItemOverlayEnabled(final ChargedItemBase chargedItem) {
        final Optional<String> visible = Optional.ofNullable(configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, chargedItem.configKey + "_overlay"));

        if (visible.isPresent() && visible.get().equals("false")) {
            return false;
        }

        return true;
    }
}
