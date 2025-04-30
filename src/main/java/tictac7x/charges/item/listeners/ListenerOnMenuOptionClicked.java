package tictac7x.charges.item.listeners;

import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnMenuOptionClicked;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.customEvents.CustomMenuOptionClicked;
import tictac7x.charges.store.Provider;

public class ListenerOnMenuOptionClicked extends ListenerBase {
    public ListenerOnMenuOptionClicked(final Provider provider, final ChargedItemBase chargedItem) {
        super(provider, chargedItem);
    }

    public void trigger(final CustomMenuOptionClicked event) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase, event)) continue;
            final OnMenuOptionClicked trigger = (OnMenuOptionClicked) triggerBase;
            boolean triggerUsed = false;

            if (trigger.menuOptionConsumer.isPresent()) {
                trigger.menuOptionConsumer.get().accept(event);
                triggerUsed = true;
            }

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final TriggerBase triggerBase, final CustomMenuOptionClicked event) {
        if (!(triggerBase instanceof OnMenuOptionClicked)) return false;
        final OnMenuOptionClicked trigger = (OnMenuOptionClicked) triggerBase;

        // Option check.
        boolean optionCheck = false;
        for (final String option : trigger.options) {
            if (event.option.equals(option)) {
                optionCheck = true;
                break;
            }
        }
        if (!optionCheck) return false;

        // Item id check.
        if (trigger.hasItemId.isPresent() && event.itemId != trigger.hasItemId.get()) {
            return false;
        }

        return super.isValidTrigger(trigger);
    }
}
