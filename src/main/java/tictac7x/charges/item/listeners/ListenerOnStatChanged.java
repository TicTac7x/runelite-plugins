package tictac7x.charges.item.listeners;

import tictac7x.charges.events.CustomStatChanged;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnStatChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnStatChanged extends ListenerBase {
    public ListenerOnStatChanged(final Provider provider) {
        super(provider);
    }

    public void trigger(final CustomStatChanged event, final ChargedItemBase chargedItem) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;
            final OnStatChanged trigger = (OnStatChanged) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final ChargedItemBase chargedItem, final TriggerBase triggerBase, final CustomStatChanged event) {
        if (!(triggerBase instanceof OnStatChanged)) return false;
        final OnStatChanged trigger = (OnStatChanged) triggerBase;

        // Skill check.
        if (trigger.skill != event.skill) {
            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
