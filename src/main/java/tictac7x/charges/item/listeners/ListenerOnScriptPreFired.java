package tictac7x.charges.item.listeners;

import net.runelite.api.events.ScriptPreFired;
import tictac7x.charges.events.CustomScriptPreFired;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnScriptPreFired;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnScriptPreFired extends ListenerBase {
    public ListenerOnScriptPreFired(final Provider provider) {
        super(provider);
    }

    public void trigger(final CustomScriptPreFired event, final ChargedItemBase chargedItem) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;
            final OnScriptPreFired trigger = (OnScriptPreFired) triggerBase;
            boolean triggerUsed = false;

            if (trigger.scriptConsumer.isPresent()) {
                trigger.scriptConsumer.get().accept(event);
                triggerUsed = true;
            }

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final ChargedItemBase chargedItem, final TriggerBase triggerBase, final CustomScriptPreFired event) {
        if (!(triggerBase instanceof OnScriptPreFired)) return false;
        final OnScriptPreFired trigger = (OnScriptPreFired) triggerBase;

        // Id check.
        if (trigger.scriptId != event.scriptId) {
            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
