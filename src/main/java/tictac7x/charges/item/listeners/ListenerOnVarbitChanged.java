package tictac7x.charges.item.listeners;

import net.runelite.api.events.VarbitChanged;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnVarbitChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnVarbitChanged extends ListenerBase {
    public ListenerOnVarbitChanged(final Provider provider) {
        super(provider);
    }

    public void trigger(final VarbitChanged event, final ChargedItemBase chargedItem) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;
            final OnVarbitChanged trigger = (OnVarbitChanged) triggerBase;
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

    public boolean isValidTrigger(final ChargedItemBase chargedItem, final TriggerBase triggerBase, final VarbitChanged event) {
        if (!(triggerBase instanceof OnVarbitChanged)) return false;
        final OnVarbitChanged trigger = (OnVarbitChanged) triggerBase;

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
