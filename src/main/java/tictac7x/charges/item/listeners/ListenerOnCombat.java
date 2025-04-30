package tictac7x.charges.item.listeners;

import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnCombat;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnCombat extends ListenerBase {
    private int ticksInCombat = 0;

    public ListenerOnCombat(final Provider provider, final ChargedItemBase chargedItem) {
        super(provider, chargedItem);
    }

    public void trigger() {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase)) continue;
            final OnCombat trigger = (OnCombat) triggerBase;
            boolean triggerUsed = false;

            if (trigger.ticksInCombat == ticksInCombat) {
                triggerUsed = true;
                ticksInCombat = 0;
            }

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final TriggerBase triggerBase) {
        if (!(triggerBase instanceof OnCombat)) return false;

        // Ticks check.
        if (++ticksInCombat != ((OnCombat) triggerBase).ticksInCombat) {
            return false;
        }

        return super.isValidTrigger(triggerBase);
    }
}
