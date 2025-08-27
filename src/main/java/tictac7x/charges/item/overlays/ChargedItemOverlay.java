package tictac7x.charges.item.overlays;

import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.ui.overlay.components.TextComponent;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ChargeId;

import java.awt.*;
import java.util.Optional;

import static tictac7x.charges.TicTac7xChargesImprovedPlugin.INFINITE_SYMBOL;

public class ChargedItemOverlay extends WidgetItemOverlay {
    private final Provider provider;
    private final ChargedItemBase[] chargedItems;

    public ChargedItemOverlay(
        final Provider provider,
        final ChargedItemBase[] chargedItems
    ) {
        this.provider = provider;
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
        if (!provider.config.showOverlays()) return;

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
        if (!chargedItem.isPresent()) return;

        if (
            // Item overlay disabled.
            !isChargedItemOverlayEnabled(chargedItem.get()) ||

            // Infinity charges hidden.
            !provider.config.showUnlimited() && chargedItem.get().getChargesString(itemId).equals(INFINITE_SYMBOL) ||
            !provider.config.showUnlimited() && triggerItem.get().fixedCharges.isPresent() && triggerItem.get().fixedCharges.get().equals(ChargeId.UNLIMITED) ||

            // Hide overlays in bank.
            !provider.config.showBankOverlays() && isBankWidget(widgetItem) ||

            // Show overlays only in bank.
            provider.config.showOverlaysOnlyInBank() && provider.client.getWidget(12, 1) == null
        ) return;

        // Get default charges from charged item.
        final String charges = chargedItem.get().getChargesString(itemId);
        final Color color = chargedItem.get().getTextColor(itemId);

        graphics.setFont(FontManager.getRunescapeSmallFont());

        final Rectangle bounds = widgetItem.getCanvasBounds();
        final TextComponent charges_component = new TextComponent();
        charges_component.setText(charges);
        final Dimension textDimension = charges_component.render(graphics);

        final int itemOverlayX = (int) ((
            provider.config.itemOverlayLocation() == TicTac7xChargesImprovedConfig.ItemOverlayLocation.BOTTOM_LEFT ||
            provider.config.itemOverlayLocation() == TicTac7xChargesImprovedConfig.ItemOverlayLocation.TOP_LEFT
        )
            ? bounds.getMinX()
            : bounds.getMaxX() - textDimension.getWidth() - 5
        );

        final int itemOverlayY = (int) ((
            provider.config.itemOverlayLocation() == TicTac7xChargesImprovedConfig.ItemOverlayLocation.TOP_LEFT ||
            provider.config.itemOverlayLocation() == TicTac7xChargesImprovedConfig.ItemOverlayLocation.TOP_RIGHT
        )
            ? bounds.getMinY() + textDimension.getHeight() - 2
            : bounds.getMaxY()
        );

        charges_component.setPosition(new Point(itemOverlayX, itemOverlayY));

        // Set color.
        charges_component.setColor(color);

        // Override for bank items.
        if (isBankWidget(widgetItem) && !chargedItem.get().getChargesString(itemId).equals("?")) {
            charges_component.setColor(provider.config.getColorDefault());
        }

        charges_component.render(graphics);

        // Charged item with storage
        renderTooltip(chargedItem.get(), widgetItem);
    }

    private void renderTooltip(final ChargedItemBase chargedItem, final WidgetItem widgetItem) {
        // Config, not storage item, empty storage checks.
        if (
            !provider.config.showStorageTooltips() ||
            !(chargedItem instanceof ChargedItemWithStorage)
        ) return;

        // Mouse position check.
        final net.runelite.api.Point mousePosition = provider.client.getMouseCanvasPosition();
        if (!widgetItem.getCanvasBounds().contains(mousePosition.getX(), mousePosition.getY())) return;


        final String tooltip = chargedItem.getTooltip();
        if (!tooltip.isEmpty()) {
            provider.tooltipManager.addFront(new Tooltip(tooltip));
        }
    }

    private boolean isChargedItemOverlayEnabled(final ChargedItemBase chargedItem) {
        final String configKey = chargedItem.getConfigKey() + TicTac7xChargesImprovedConfig._overlay;
        final Optional<String> visible = Optional.ofNullable(provider.configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, configKey));
        return visible.isPresent() && visible.get().equals("true");
    }
}
