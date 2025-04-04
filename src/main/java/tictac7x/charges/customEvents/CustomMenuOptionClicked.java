package tictac7x.charges.customEvents;

import net.runelite.api.Client;
import net.runelite.api.events.MenuOptionClicked;

public class CustomMenuOptionClicked {
    public final int eventId;
    public final String target;
    public final String option;
    public final int actionId;
    public final String action;
    public final int itemId;
    public final int impostorId;

    public CustomMenuOptionClicked(final MenuOptionClicked event, final int impostorId) {
        this.eventId = event.getId();
        this.target = event.getMenuTarget().replaceAll("</?col.*?>", "");
        this.option = event.getMenuOption().replaceAll("</?col.*?>", "");
        this.actionId = event.getMenuAction().getId();
        this.action = event.getMenuAction().name();
        this.itemId = event.getItemId();
        this.impostorId = impostorId;
    }

    @Override
    public String toString() {
        return ("MENU OPTION CLICKED | " +
            "event id: " + eventId +
            ", option: \"" + option + "\"" +
            ", target: \"" + target + "\"" +
            ", action id: " + actionId +
            ", action name: \"" + action + "\"" +
            ", item id: " + itemId +
            ", impostor id: " + impostorId
        );
    }
}
