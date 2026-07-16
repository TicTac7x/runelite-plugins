package tictac7x.charges.item.listeners;

import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ListenerOnXpDrop extends ListenerBase {
    public ListenerOnXpDrop(Provider provider) {
        super(provider);
    }

    public void trigger(CustomStatChanged event, ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;
            OnXpDrop trigger = (OnXpDrop) triggerBase;
            boolean triggerUsed = false;

            if (trigger.xpAmountConsumer.isPresent()) {
                trigger.xpAmountConsumer.get().accept(event.xpDrop);
                triggerUsed = true;
            }

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) {
                afterTrigger(trigger);
                return;
            }
        }
    }

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, CustomStatChanged event) {
        if (!(triggerBase instanceof OnXpDrop)) return false;
        OnXpDrop trigger = (OnXpDrop) triggerBase;

        // Skill check.
        if (trigger.skill != event.skill) {
            return false;
        }

        // XP drop check.
        if (event.xpDrop == 0) {
            return false;
        }

        // Amount check.
        if (trigger.amount.isPresent() && trigger.amount.get() != event.xpDrop
        ) {
            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
