package tictac7x.charges.item.listeners;

import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnUserAction;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnUserAction extends ListenerBase {
    public ListenerOnUserAction(final Provider provider) {
        super(provider);
    }

    public void trigger(final ChargedItemBase chargedItem) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase)) continue;

            final OnUserAction trigger = (OnUserAction) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final ChargedItemBase chargedItem, final TriggerBase triggerBase) {
        if (!(triggerBase instanceof OnUserAction)) return false;
        final OnUserAction trigger = (OnUserAction) triggerBase;
        return super.isValidTrigger(trigger, chargedItem);
    }
}
