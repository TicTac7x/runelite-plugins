package tictac7x.storage.panel;

import net.runelite.client.events.ConfigChanged;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import tictac7x.storage.TicTac7xStorageConfig;

public class PanelNavigationButton {
    private final ClientToolbar clientToolbar;
    private final TicTac7xStorageConfig config;
    private final StoragePanel storagePanel;

    private NavigationButton navigationButton;

    public PanelNavigationButton(final ClientToolbar clientToolbar, final TicTac7xStorageConfig config, final StoragePanel storagePanel) {
        this.clientToolbar = clientToolbar;
        this.config = config;
        this.storagePanel = storagePanel;
        updateNavigationButton();
    }

    private void updateNavigationButton() {
        this.navigationButton = NavigationButton.builder()
            .tooltip("Storage")
            .icon(ImageUtil.loadImageResource(getClass(), "/casket.png"))
            .priority(config.getPanelPriority())
            .panel(storagePanel)
            .build();

        if (config.showPanel()) {
            clientToolbar.addNavigation(navigationButton);
        } else {
            clientToolbar.removeNavigation(navigationButton);
        }
    }

    public void onConfigChanged(final ConfigChanged event) {
        if (event.getKey().equals(TicTac7xStorageConfig.panel) || event.getKey().equals(TicTac7xStorageConfig.panel_priority)) {
            updateNavigationButton();
        }
    }

    public void shutDown() {
        clientToolbar.removeNavigation(navigationButton);
    }
}
