package tictac7x.charges.events;

import net.runelite.api.Client;
import net.runelite.api.events.MenuOptionClicked;

import java.util.Optional;

public class CustomMenuOptionClicked {
    public final int eventId;
    public final String target;
    public final String option;
    public final int actionId;
    public final String actionName;
    public final int itemId;
    public final int impostorId;

    public Optional<Integer> usedItemId = Optional.empty();

    public CustomMenuOptionClicked(
        final int eventId,
        final String target,
        final String option,
        final int actionId,
        final String actionName,
        final int itemId,
        final int impostorId
    ) {
        this.eventId = eventId;
        this.target = target;
        this.option = option;
        this.actionId = actionId;
        this.actionName = actionName;
        this.itemId = itemId;
        this.impostorId = impostorId;
    }

    public void assignUsedItemId(final int usedItemId) {
        this.usedItemId = Optional.of(usedItemId);
    }

    @Override
    public String toString() {
        return ("MENU OPTION CLICKED | " +
            "event id: " + eventId +
            ", option: \"" + option + "\"" +
            ", target: \"" + target + "\"" +
            ", action id: " + actionId +
            ", action name: \"" + actionName + "\"" +
            ", item id: " + itemId +
            ", used item id: " + usedItemId +
            ", impostor id: " + impostorId
        );
    }
}
