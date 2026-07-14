package tictac7x.charges.item.listeners;

import net.runelite.api.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ListenerOnVarbitChanged extends ListenerBase {
    public ListenerOnVarbitChanged(Provider provider) {
        super(provider);
    }

    public void trigger(VarbitChanged event, ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;
            OnVarbitChanged trigger = (OnVarbitChanged) triggerBase;
            boolean triggerUsed = false;

            // Set dynamically.
            if (trigger.setDynamically.isPresent() && chargedItem instanceof ChargedItem) {
                ((ChargedItem) chargedItem).setCharges(event.getValue());
            }

            // Varbit value consumer.
            if (trigger.varbitValueConsumer.isPresent()) {
                trigger.varbitValueConsumer.get().accept(event.getValue());
                triggerUsed = true;
            }

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, VarbitChanged event) {
        if (!(triggerBase instanceof OnVarbitChanged)) return false;
        OnVarbitChanged trigger = (OnVarbitChanged) triggerBase;

        // Varbit id check.
        if (event.getVarbitId() != trigger.varbitId) {
            return false;
        }

        // Varbit value check.
        if (trigger.varbitValue.isPresent() && event.getValue() != trigger.varbitValue.get()) {
            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
