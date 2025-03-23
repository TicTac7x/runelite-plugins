package tictac7x.storage.panel;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.util.function.Consumer;

public class PanelSearch {
    private final IconTextField search;

    public PanelSearch(final Consumer<String> onSearch) {
        search = new IconTextField();
        search.setIcon(IconTextField.Icon.SEARCH);
        search.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH, 30));
        search.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        search.setHoverBackgroundColor(ColorScheme.DARK_GRAY_HOVER_COLOR);
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                onSearch.accept(search.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                onSearch.accept(search.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                onSearch.accept(search.getText());
            }
        });
    }

    public IconTextField get() {
        return search;
    }
}