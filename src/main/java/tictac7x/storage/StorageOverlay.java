package tictac7x.storage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import tictac7x.storage.storage.Storage;
import tictac7x.storage.storage.StorageItem;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private final List<ImageComponent> images = new ArrayList<>();

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
        try {
            // List of images to render.
            List<ImageComponent> images = new ArrayList<>();

            for (final StorageItem item : storage.getItems()) {
                final int itemId = item.id;
                final int itemQuantity = item.getQuantity();

                // Item not shown.
                if (!isVisible(itemId) || isHidden(itemId)) continue;

                images.add(new ImageComponent(this.itemManager.getImage(itemId, itemQuantity, true)));
            }

            // Replace old images with new ones.
            this.images.clear();
            this.images.addAll(images);
        } catch (final Exception ignored) {}
    }

    private String[] getVisibleItems() {
        String[] visible = new String[]{};
        try { visible = configManager.getConfiguration(TicTac7xStorageConfig.group, this.configKey + "_" + TicTac7xStorageConfig.visible).split(",");
        } catch (final Exception ignored) {}

        return visible;
    }

    private String[] getHiddenItems() {
        String[] hidden = new String[]{};
        try { hidden = configManager.getConfiguration(TicTac7xStorageConfig.group, this.configKey + "_" + TicTac7xStorageConfig.hidden).split(",");
        } catch (final Exception ignored) {}

        return hidden;
    }

    private boolean isVisible(final int item_id) {
        final String[] visible = this.getVisibleItems();
        final ItemComposition item = this.itemManager.getItemComposition(item_id);

        // Visible list not used.
        if (visible.length == 0 || visible.length == 1 && visible[0].equals("")) return true;

        // Check if visible.
        for (final String name : visible) {
            if (item.getName().contains(name)) {
                return true;
            }
        }

        // Not visible.
        return false;
    }

    private boolean isHidden(final int item_id) {
        final String[] hidden = this.getHiddenItems();
        final ItemComposition item = this.itemManager.getItemComposition(item_id);

        // Hidden list not used.
        if (hidden.length == 0 || hidden.length == 1 && hidden[0].equals("")) return false;

        // Check if hidden.
        for (final String name : hidden) {
            if (item.getName().contains(name)) {
                return true;
            }
        }

        // Not Hidden.
        return false;
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

    private void loadStorageFromConfig() {
        storage = new Storage(configKey, itemContainerId, itemManager, configManager).loadStorageFromConfig();
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
