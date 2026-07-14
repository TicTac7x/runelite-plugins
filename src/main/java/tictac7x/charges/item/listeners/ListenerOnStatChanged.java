package tictac7x.charges.item.listeners;

import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ListenerOnStatChanged extends ListenerBase {
    public ListenerOnStatChanged(Provider provider) {
        super(provider);
    }

    public void trigger(CustomStatChanged event, ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;
            OnStatChanged trigger = (OnStatChanged) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, CustomStatChanged event) {
        if (!(triggerBase instanceof OnStatChanged)) return false;
        OnStatChanged trigger = (OnStatChanged) triggerBase;

        // Skill check.
        if (trigger.skill != event.skill) {
            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
