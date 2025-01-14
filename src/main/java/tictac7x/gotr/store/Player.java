package tictac7x.gotr.store;

import net.runelite.api.Client;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;

import java.util.Optional;

public class Player {
    private final int GOTR_WIDGET_PARENT = 746;
    private final int GOTR_WIDGET_CHILD = 2;

    private final Client client;

    private Optional<Widget> gotrWidget = Optional.empty();

    public Player(final Client client) {
        this.client = client;
    }

    public void onWidgetLoaded(final WidgetLoaded event) {
        gotrWidget = Optional.ofNullable(client.getWidget(GOTR_WIDGET_PARENT, GOTR_WIDGET_CHILD));
    }

    public boolean inGotr() {
        return gotrWidget.isPresent() && !gotrWidget.get().isHidden();
    }
}
