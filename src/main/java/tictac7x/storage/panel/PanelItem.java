package tictac7x.storage.panel;

import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.PluginPanel;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Dimension;

public class PanelItem extends JLabel {
    public PanelItem(final int itemId, final int itemQuantity, final ClientThread clientThread, final ItemManager itemManager) {
        setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 32));

        clientThread.invoke(() -> {
            setIcon(new ImageIcon(itemManager.getImage(itemId, itemQuantity, true)));
            setText(itemManager.getItemComposition(itemId).getName());

            revalidate();
            repaint();
        });
    }
}
