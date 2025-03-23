package tictac7x.storage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.ItemContainer;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ComponentOrientation;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Storage extends OverlayPanel {
    private final String storage_id;
    protected final int item_container_id;
    private final int[] widgetIds;
    private final Client client;
    private final ClientThread client_thread;
    private final ConfigManager configs;
    protected final TicTac7xStorageConfig config;
    private final ItemManager items;

    private final int PLACEHOLDER_TEMPLATE_ID = 14401;
    protected final PanelComponent itemsPanelComponent = new PanelComponent();
    private final List<ImageComponent> images = new ArrayList<>();
    private final JsonParser parser = new JsonParser();

    public Storage(final String storage_id, final InventoryID item_container_id, final int[] widgetIds, final Client client, final ClientThread client_thread, final ConfigManager configs, final TicTac7xStorageConfig config, final ItemManager items) {
        this.storage_id = storage_id;
        this.item_container_id = item_container_id.getId();
        this.widgetIds = widgetIds;
        this.client = client;
        this.client_thread = client_thread;
        this.configs = configs;
        this.config = config;
        this.items = items;

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

        // Generate images based on config.
        this.client_thread.invokeLater(() -> this.updateImages(configs.getConfiguration(TicTac7xStorageConfig.group, storage_id)));

    }

    public void onItemContainerChanged(final ItemContainerChanged event) {
        if (event.getContainerId() != this.item_container_id) return;

        final ItemContainer item_container = event.getItemContainer();
        final JsonObject json = new JsonObject();

        for (final Item item : item_container.getItems()) {
            // Empty item.
            if (item.getId() == -1 || item.getQuantity() == 0) continue;

            // Placeholder.
            if (items.getItemComposition(item.getId()).getPlaceholderTemplateId() == PLACEHOLDER_TEMPLATE_ID) continue;

            // Save item.
            final String id = String.valueOf(item.getId());
            if (!json.has(id)) {
                json.addProperty(id, item_container.count(item.getId()));
            }
        }

        configs.setConfiguration(TicTac7xStorageConfig.group, this.storage_id, json.toString());
    }

    public void onConfigChanged(final ConfigChanged event) {
        if (
            !event.getGroup().equals(TicTac7xStorageConfig.group) |
                !event.getKey().equals(this.storage_id) &&
                !event.getKey().equals(this.storage_id + "_" + TicTac7xStorageConfig.visible) &&
                !event.getKey().equals(this.storage_id + "_" + TicTac7xStorageConfig.hidden)
        ) return;

        this.client_thread.invokeLater(() -> this.updateImages(configs.getConfiguration(TicTac7xStorageConfig.group, this.storage_id)));
    }

    private void updateImages(final String items) {
        // List of images to render.
        List<ImageComponent> images = new ArrayList<>();

        final JsonObject json = parser.parse(items).getAsJsonObject();
        for (final Map.Entry<String, JsonElement> entry : json.entrySet()) {
            final int item_id = Integer.parseInt(entry.getKey());
            final int item_quantity = entry.getValue().getAsInt();

            // Item not shown.
            if (!isVisible(item_id) || isHidden(item_id)) continue;

            images.add(new ImageComponent(this.items.getImage(item_id, item_quantity, true)));
        }

        // Replace old images with new ones.
        this.images.clear();
        this.images.addAll(images);
    }

    private String[] getVisibleItems() {
        String[] visible = new String[]{};
        try { visible = configs.getConfiguration(TicTac7xStorageConfig.group, this.storage_id + "_" + TicTac7xStorageConfig.visible).split(",");
        } catch (final Exception ignored) {}

        return visible;
    }

    private String[] getHiddenItems() {
        String[] hidden = new String[]{};
        try { hidden = configs.getConfiguration(TicTac7xStorageConfig.group, this.storage_id + "_" + TicTac7xStorageConfig.hidden).split(",");
        } catch (final Exception ignored) {}

        return hidden;
    }

    private boolean isVisible(final int item_id) {
        final String[] visible = this.getVisibleItems();
        final ItemComposition item = this.items.getItemComposition(item_id);

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
        final ItemComposition item = this.items.getItemComposition(item_id);

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
        return Boolean.parseBoolean(configs.getConfiguration(TicTac7xStorageConfig.group, this.storage_id + "_" + TicTac7xStorageConfig.show));
    }

    private boolean autoHide() {
        return Boolean.parseBoolean(configs.getConfiguration(TicTac7xStorageConfig.group, this.storage_id + "_" + TicTac7xStorageConfig.auto_hide));
    }

    private boolean isWidgetVisible() {
        final Optional<Widget> widget = Optional.ofNullable(client.getWidget(widgetIds[0], widgetIds[1]));
        return (widget.isPresent() && !widget.get().isHidden());
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

    protected void renderBefore() {}
    protected void renderAfter() {}
}
