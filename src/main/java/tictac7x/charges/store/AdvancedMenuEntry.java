package tictac7x.charges.store;

import net.runelite.api.Client;
import net.runelite.api.events.MenuOptionClicked;

public class AdvancedMenuEntry {
    public final int eventId;
    public final String target;
    public final String option;
    public final int actionId;
    public final String action;
    public final int itemId;
    public final int impostorId;

    public AdvancedMenuEntry(final MenuOptionClicked event, final Client client) {
        this.eventId = event.getId();
        this.target = event.getMenuTarget().replaceAll("</?col.*?>", "");
        this.option = event.getMenuOption().replaceAll("</?col.*?>", "");
        this.actionId = event.getMenuAction().getId();
        this.action = event.getMenuAction().name();
        this.itemId = event.getItemId();

        int impostorId = -1;
        try {
            impostorId = client.getObjectDefinition(event.getMenuEntry().getIdentifier()).getImpostor().getId();
        } catch (final Exception ignored) {}
        this.impostorId = impostorId;
    }
}
