package tictac7x.charges.item.overlays;

import net.runelite.api.widgets.*;
import net.runelite.client.ui.*;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.components.TextComponent;
import net.runelite.client.ui.overlay.tooltip.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.awt.*;
import java.util.*;

import static tictac7x.charges.TicTac7xChargesImprovedPlugin.INFINITE_SYMBOL;

public class ChargedItemOverlay extends WidgetItemOverlay {
    private Provider provider;
    private ChargedItemBase[] chargedItems;

    public ChargedItemOverlay(
        Provider provider,
        ChargedItemBase[] chargedItems
    ) {
        this.provider = provider;
        this.chargedItems = chargedItems;
        showOnInventory();
        showOnEquipment();
        showOnInterfaces(84);
        showOnBank();
    }

    private boolean isBankWidget(WidgetItem item_widget) {
        return
            item_widget.getWidget().getParentId() == 786442 ||
            item_widget.getWidget().getParentId() == 786443 ||
            item_widget.getWidget().getParentId() == 786444 ||
            item_widget.getWidget().getParentId() == 786445
        ;
    }


    @Override
    public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem) {
        if (!provider.config.showOverlays()) return;

        Optional<ChargedItemBase> chargedItem = Optional.empty();
        Optional<TriggerItem> triggerItem = Optional.empty();

        // Find correct charged item.
        chargedItemFinder: for (ChargedItemBase chargedItemBase : chargedItems) {
            for (TriggerItem chargedItemTriggerItem : chargedItemBase.items) {
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
        String charges = chargedItem.get().getChargesString(itemId);
        Color color = chargedItem.get().getTextColor(itemId);

        graphics.setFont(FontManager.getRunescapeSmallFont());

        Rectangle bounds = widgetItem.getCanvasBounds();
        net.runelite.client.ui.overlay.components.TextComponent charges_component = new TextComponent();
        charges_component.setText(charges);
        Dimension textDimension = charges_component.render(graphics);

        int itemOverlayX = (int) ((
            provider.config.itemOverlayLocation() == TicTac7xChargesImprovedConfig.ItemOverlayLocation.BOTTOM_LEFT ||
            provider.config.itemOverlayLocation() == TicTac7xChargesImprovedConfig.ItemOverlayLocation.TOP_LEFT
        )
            ? bounds.getMinX()
            : bounds.getMaxX() - textDimension.getWidth() - 5
        );

        int itemOverlayY = (int) ((
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

    private void renderTooltip(ChargedItemBase chargedItem, WidgetItem widgetItem) {
        // Config, not storage item, empty storage checks.
        if (
            !provider.config.showStorageTooltips() ||
            !(chargedItem instanceof ChargedItemWithStorage)
        ) return;

        // Mouse position check.
        net.runelite.api.Point mousePosition = provider.client.getMouseCanvasPosition();
        if (!widgetItem.getCanvasBounds().contains(mousePosition.getX(), mousePosition.getY())) return;


        String tooltip = chargedItem.getTooltip();
        if (!tooltip.isEmpty()) {
            provider.tooltipManager.addFront(new Tooltip(tooltip));
        }
    }

    private boolean isChargedItemOverlayEnabled(ChargedItemBase chargedItem) {
        String configKey = chargedItem.getConfigKey() + TicTac7xChargesImprovedConfig._overlay;
        Optional<String> visible = Optional.ofNullable(provider.configManager.getConfiguration(configKey));
        return visible.isPresent() && visible.get().equals("true");
    }
}
