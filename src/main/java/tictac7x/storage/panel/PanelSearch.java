package tictac7x.storage.panel;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.util.function.Consumer;

public class PanelSearch extends JPanel {
    private final Consumer<String> onSearch;
    private final IconTextField search;

    public PanelSearch(final Consumer<String> onSearch) {
        this.onSearch = onSearch;
        this.search = new IconTextField();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));

        search.setIcon(IconTextField.Icon.SEARCH);
        search.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH + 3, 30));
        search.setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH + 3, 30));
        search.setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH + 3, 30));
        search.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        search.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR, 1));

        // Swap the order in which the borders are applied
        search.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR, 1));
        search.setHoverBackgroundColor(ColorScheme.DARK_GRAY_HOVER_COLOR);

        search.getDocument().addDocumentListener(searchListener());
        add(search);
    }

    private DocumentListener searchListener() {
        return new DocumentListener() {
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
        };
    }
}