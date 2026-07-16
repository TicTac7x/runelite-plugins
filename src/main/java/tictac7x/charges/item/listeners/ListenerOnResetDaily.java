package tictac7x.charges.item.listeners;

import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ListenerOnResetDaily extends ListenerBase {
    public ListenerOnResetDaily(Provider provider) {
        super(provider);
    }

    public void trigger(ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase)) continue;
            OnResetDaily trigger = (OnResetDaily) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) {
                afterTrigger(trigger);
                return;
            }
        }
    }

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase) {
        if (!(triggerBase instanceof OnResetDaily)) return false;
        OnResetDaily trigger = (OnResetDaily) triggerBase;

        if (trigger.resetSpecificItem.isPresent() && !provider.store.itemInPossession(trigger.resetSpecificItem.get())) {
            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
