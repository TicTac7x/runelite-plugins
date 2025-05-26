package tictac7x.storage.overlays;

import net.runelite.api.widgets.Widget;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ComponentOrientation;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import tictac7x.storage.TicTac7xStorageConfig;
import tictac7x.storage.storage.Storage;
import tictac7x.storage.storage.StorageItem;
import tictac7x.storage.utils.Provider;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StorageOverlay extends OverlayPanel {
    final Provider provider;
    private final String configKey;
    protected final Storage storage;
    private final int[] widgetIds;

    protected final PanelComponent itemsPanelComponent = new PanelComponent();
    private List<ImageComponent> images = new ArrayList<>();

    public StorageOverlay(final String configKey, final Storage storage, final int[] widgetIds, final Provider provider) {
        this.configKey = configKey;
        this.storage = storage;
        this.widgetIds = widgetIds;
        this.provider = provider;

        // Overlay configuration.
        setPreferredPosition(OverlayPosition.BOTTOM_RIGHT);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        panelComponent.setGap(new Point(0, 10));
        panelComponent.setOrientation(ComponentOrientation.VERTICAL);
        panelComponent.setBorder(new Rectangle(10, 10, 6, 10));
        itemsPanelComponent.setWrap(true);
        itemsPanelComponent.setBackgroundColor(null);
        itemsPanelComponent.setGap(new Point(6, 4));
        itemsPanelComponent.setOrientation(ComponentOrientation.HORIZONTAL);
        itemsPanelComponent.setBorder(new Rectangle(0,0,0,0));

        storage.addOnChangeListener(this::updateImages);
    }

    public void onConfigChanged(final ConfigChanged event) {
        if (
            event.getKey().equals(configKey + TicTac7xStorageConfig.visible) ||
            event.getKey().equals(configKey + TicTac7xStorageConfig.hidden)
        ) {
            updateImages();
        }
    }

    private void updateImages() {
        final Optional<String> visibleString = Optional.ofNullable(provider.configManager.getConfiguration(TicTac7xStorageConfig.group, this.configKey + TicTac7xStorageConfig.visible));
        final Optional<String> hiddenString = Optional.ofNullable(provider.configManager.getConfiguration(TicTac7xStorageConfig.group, this.configKey + TicTac7xStorageConfig.hidden));

        provider.clientThread.invoke(() -> {
            final List<ImageComponent> images = new ArrayList<>();

            for (final StorageItem item : storage.getItems(visibleString.orElse(""), hiddenString.orElse(""), false)) {
                if (showItem(item)) {
                    images.add(new ImageComponent(provider.itemManager.getImage(item.id, item.getQuantity(), true)));
                }
            }

            this.images = images;
        });
    }

    boolean showItem(final StorageItem item) {
        return true;
    }

    private boolean show() {
        return Boolean.parseBoolean(provider.configManager.getConfiguration(TicTac7xStorageConfig.group, this.configKey + TicTac7xStorageConfig.show));
    }

    private boolean autoHide() {
        return Boolean.parseBoolean(provider.configManager.getConfiguration(TicTac7xStorageConfig.group, this.configKey + TicTac7xStorageConfig.auto_hide));
    }

    private boolean isWidgetVisible() {
        final Optional<Widget> widget = Optional.ofNullable(provider.client.getWidget(widgetIds[0], widgetIds[1]));
        return (widget.isPresent() && !widget.get().isHidden());
    }

    @Override
    public Dimension render(final Graphics2D graphics) {
        if (!show() || autoHide() && isWidgetVisible()) return null;

        panelComponent.getChildren().clear();
        itemsPanelComponent.getChildren().clear();

        renderBefore();

        images.forEach(image -> itemsPanelComponent.getChildren().add(image));
        panelComponent.getChildren().add(itemsPanelComponent);

        renderAfter();

        if (itemsPanelComponent.getChildren().size() == 0) return null;
        return super.render(graphics);
    }

    protected void renderBefore() {}

    protected void renderAfter() {}
}
