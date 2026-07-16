package tictac7x.charges.item.listeners;

import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ListenerOnUserAction extends ListenerBase {
    public ListenerOnUserAction(Provider provider) {
        super(provider);
    }

    public void trigger(ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase)) continue;

            OnUserAction trigger = (OnUserAction) triggerBase;
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
        if (!(triggerBase instanceof OnUserAction)) return false;
        OnUserAction trigger = (OnUserAction) triggerBase;
        return super.isValidTrigger(trigger, chargedItem);
    }
}
