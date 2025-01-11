package tictac7x.balloon;

import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.InfoBox;

import java.awt.Color;

public class BalloonInfoBox extends InfoBox {
    private final ConfigManager configManager;
    private final TicTac7xBalloonConfig config;
    private final Balloon balloon;

    private final String logConfigKeyName;
    private final String tooltip;
    private boolean renderRecently = false;

    public BalloonInfoBox(final int item_id, final String logConfigKeyName, final String tooltip, final ConfigManager configManager, final TicTac7xBalloonConfig config, final ItemManager items, final Balloon balloon, final Plugin plugin) {
        super(items.getImage(item_id), plugin);
        this.configManager = configManager;
        this.config = config;
        this.balloon = balloon;

        this.logConfigKeyName = logConfigKeyName;
        this.tooltip = tooltip;
    }

    @Override
    public String getName() {
        return super.getName() + "_" + this.logConfigKeyName;
    }

    @Override
    public String getTooltip() {
        return this.tooltip;
    }

    @Override
    public String getText() {
        return String.valueOf(this.getCount());
    }

    @Override
    public Color getTextColor() {
        return this.getCount() > 0 ? Color.lightGray : Color.red;
    }

    @Override
    public boolean render() {
        return
            config.show() == TicTac7xBalloonConfig.Show.ALL_THE_TIME ||
            config.show() == TicTac7xBalloonConfig.Show.NEAR_THE_BALLOON && balloon.isVisible() ||
            config.show() == TicTac7xBalloonConfig.Show.RECENTLY_USED && this.renderRecently;
    }

    private int getCount() {
        return Integer.parseInt(configManager.getConfiguration(TicTac7xBalloonConfig.group, this.logConfigKeyName));
    }

    // Logs count changed and infoboxes are shown based on recently used.
    public void onConfigChanged(final ConfigChanged event) {
        if (
            event.getGroup().equals(TicTac7xBalloonConfig.group) &&
            event.getKey().equals(this.logConfigKeyName) &&
            config.show() == TicTac7xBalloonConfig.Show.RECENTLY_USED
        ) {
            // Start showing infobox.
            this.renderRecently = true;

            new Thread(() -> {
                try {
                    // Hide the infobox after specified time.
                    Thread.sleep(60L * config.showRecentlyUsedForMinutes() * 1000);
                    this.renderRecently = false;
                } catch (final Exception ignored) {}
            }).start();
        }
    }
}
