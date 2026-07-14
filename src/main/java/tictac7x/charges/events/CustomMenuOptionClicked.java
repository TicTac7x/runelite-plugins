package tictac7x.charges.events;

import java.util.*;

public class CustomMenuOptionClicked {
    public int eventId;
    public String target;
    public String option;
    public int actionId;
    public String actionName;
    public int itemId;
    public int impostorId;

    public Optional<Integer> usedItemId = Optional.empty();

    public CustomMenuOptionClicked(
        int eventId,
        String target,
        String option,
        int actionId,
        String actionName,
        int itemId,
        int impostorId
    ) {
        this.eventId = eventId;
        this.target = target;
        this.option = option;
        this.actionId = actionId;
        this.actionName = actionName;
        this.itemId = itemId;
        this.impostorId = impostorId;
    }

    public void assignUsedItemId(int usedItemId) {
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
