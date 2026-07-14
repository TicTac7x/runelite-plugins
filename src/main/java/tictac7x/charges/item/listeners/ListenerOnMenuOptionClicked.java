package tictac7x.charges.item.listeners;

import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ListenerOnMenuOptionClicked extends ListenerBase {
    public ListenerOnMenuOptionClicked(Provider provider) {
        super(provider);
    }

    public void trigger(CustomMenuOptionClicked event, ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;
            OnMenuOptionClicked trigger = (OnMenuOptionClicked) triggerBase;
            boolean triggerUsed = false;

            if (trigger.menuOptionConsumer.isPresent()) {
                trigger.menuOptionConsumer.get().accept(event);
                triggerUsed = true;
            }

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, CustomMenuOptionClicked event) {
        if (!(triggerBase instanceof OnMenuOptionClicked)) return false;
        OnMenuOptionClicked trigger = (OnMenuOptionClicked) triggerBase;

        // Option check.
        boolean optionCheck = false;
        for (String option : trigger.options) {
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

        return super.isValidTrigger(trigger, chargedItem);
    }
}
