package tictac7x.charges.item.listeners;

import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnResetDaily;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnResetDaily extends ListenerBase {
    public ListenerOnResetDaily(final Provider provider, final ChargedItemBase chargedItem) {
        super(provider, chargedItem);
    }

    public boolean trigger() {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase)) continue;
            final OnResetDaily trigger = (OnResetDaily) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed) return true;
        }

        return false;
    }

    public boolean isValidTrigger(final TriggerBase triggerBase) {
        if (!(triggerBase instanceof OnResetDaily)) return false;
        final OnResetDaily trigger = (OnResetDaily) triggerBase;

        if (trigger.resetSpecificItem.isPresent() && !provider.store.itemInPossession(trigger.resetSpecificItem.get())) {
            return false;
        }

        return super.isValidTrigger(trigger);
    }
}
