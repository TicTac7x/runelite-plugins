package tictac7x.storage.overlays;

import net.runelite.api.Client;
import net.runelite.api.ItemComposition;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ComponentOrientation;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import tictac7x.storage.TicTac7xStorageConfig;
import tictac7x.storage.storage.Storage;
import tictac7x.storage.storage.StorageFromConfig;
import tictac7x.storage.storage.StorageItem;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StorageOverlay extends OverlayPanel {
    private final String configKey;
    protected final int itemContainerId;
    private final int[] widgetIds;
    private final Client client;
    private final ClientThread clientThread;
    private final OverlayManager overlayManager;
    private final ConfigManager configManager;
    protected final TicTac7xStorageConfig config;
    private final ItemManager itemManager;

    protected final PanelComponent itemsPanelComponent = new PanelComponent();
    private List<ImageComponent> images = new ArrayList<>();

    protected Storage storage;

    public StorageOverlay(final String configKey, final int itemContainerId, final int[] widgetIds, final Client client, final ClientThread clientThread, final OverlayManager overlayManager, final ConfigManager configManager, final ItemManager itemManager, final TicTac7xStorageConfig config) {
        this.configKey = configKey;
        this.itemContainerId = itemContainerId;
        this.widgetIds = widgetIds;
        this.client = client;
        this.clientThread = clientThread;
        this.overlayManager = overlayManager;
        this.configManager = configManager;
        this.itemManager = itemManager;
        this.config = config;

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

        clientThread.invoke(() -> {
            loadStorageFromConfig();
            updateImages();
        });

        overlayManager.add(this);
    }

    public void onConfigChanged(final ConfigChanged event) {
        if (
            event.getKey().equals(configKey + TicTac7xStorageConfig.storage) ||
            event.getKey().equals(configKey + "_" + TicTac7xStorageConfig.visible) ||
            event.getKey().equals(configKey + "_" + TicTac7xStorageConfig.hidden)
        ) {
            clientThread.invoke(() -> {
                loadStorageFromConfig();
                updateImages();
            });
        }
    }

    private void updateImages() {
        final String visibleString = configManager.getConfiguration(TicTac7xStorageConfig.group, this.configKey + "_" + TicTac7xStorageConfig.visible);
        final String hiddenString = configManager.getConfiguration(TicTac7xStorageConfig.group, this.configKey + "_" + TicTac7xStorageConfig.hidden);

        final List<ImageComponent> images = new ArrayList<>();

        for (final StorageItem item : storage.getItems(visibleString, hiddenString, true, false)) {
            images.add(new ImageComponent(this.itemManager.getImage(item.id, item.getQuantity(), true)));
        }

        this.images = images;
    }

    private boolean show() {
        return Boolean.parseBoolean(configManager.getConfiguration(TicTac7xStorageConfig.group, this.configKey + "_" + TicTac7xStorageConfig.show));
    }

    private boolean autoHide() {
        return Boolean.parseBoolean(configManager.getConfiguration(TicTac7xStorageConfig.group, this.configKey + "_" + TicTac7xStorageConfig.auto_hide));
    }

    private boolean isWidgetVisible() {
        final Optional<Widget> widget = Optional.ofNullable(client.getWidget(widgetIds[0], widgetIds[1]));
        return (widget.isPresent() && !widget.get().isHidden());
    }

    protected void loadStorageFromConfig() {
        storage = new StorageFromConfig(configKey, itemContainerId, itemManager, configManager);
    }

    @Override
    public Dimension render(final Graphics2D graphics) {
        if (!show()) return null;
        if (autoHide() && isWidgetVisible()) return null;

        panelComponent.getChildren().clear();
        itemsPanelComponent.getChildren().clear();

        renderBefore();

        this.images.forEach(image -> itemsPanelComponent.getChildren().add(image));
        panelComponent.getChildren().add(itemsPanelComponent);

        renderAfter();

        if (itemsPanelComponent.getChildren().size() == 0) return null;
        return super.render(graphics);
    }

    public void shutDown() {
        overlayManager.remove(this);
    }

    protected void renderBefore() {}

    protected void renderAfter() {}
}
