package tictac7x.storage.panel;

import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.PluginPanel;
import tictac7x.storage.storage.StorageItem;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Dimension;

public class PanelItem extends JLabel {
    public PanelItem(final StorageItem item, final ClientThread clientThread, final ItemManager itemManager) {
        setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 32));

        clientThread.invoke(() -> {
            setIcon(new ImageIcon(itemManager.getImage(item.id, item.getQuantity(), true)));
            setText(item.name);
            revalidate();
            repaint();
        });
    }
}
