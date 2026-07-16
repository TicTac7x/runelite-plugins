package tictac7x.charges.item.listeners;

import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ListenerOnScriptPreFired extends ListenerBase {
    public ListenerOnScriptPreFired(Provider provider) {
        super(provider);
    }

    public void trigger(CustomScriptPreFired event, ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;
            OnScriptPreFired trigger = (OnScriptPreFired) triggerBase;
            boolean triggerUsed = false;

            if (trigger.scriptConsumer.isPresent()) {
                trigger.scriptConsumer.get().accept(event);
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

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, CustomScriptPreFired event) {
        if (!(triggerBase instanceof OnScriptPreFired)) return false;
        OnScriptPreFired trigger = (OnScriptPreFired) triggerBase;

        // Id check.
        if (trigger.scriptId != event.scriptId) {
            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
