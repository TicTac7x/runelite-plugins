package tictac7x.charges.item.listeners;

import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnResetDaily;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnResetDaily extends ListenerBase {
    public ListenerOnResetDaily(final Provider provider) {
        super(provider);
    }

    public boolean trigger(final ChargedItemBase chargedItem) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase)) continue;
            final OnResetDaily trigger = (OnResetDaily) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return true;
        }

        return false;
    }

    public boolean isValidTrigger(final ChargedItemBase chargedItem, final TriggerBase triggerBase) {
        if (!(triggerBase instanceof OnResetDaily)) return false;
        final OnResetDaily trigger = (OnResetDaily) triggerBase;

        if (trigger.resetSpecificItem.isPresent() && !provider.store.itemInPossession(trigger.resetSpecificItem.get())) {
            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
