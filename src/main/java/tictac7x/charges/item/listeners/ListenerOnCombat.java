package tictac7x.charges.item.listeners;

import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ListenerOnCombat extends ListenerBase {
    private int ticksInCombat = 0;

    public ListenerOnCombat(Provider provider) {
        super(provider);
    }

    public void trigger(ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase)) continue;
            OnCombat trigger = (OnCombat) triggerBase;
            boolean triggerUsed = false;

            if (trigger.ticksInCombat == ticksInCombat) {
                triggerUsed = true;
                ticksInCombat = 0;
            }

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase) {
        if (!(triggerBase instanceof OnCombat)) return false;

        // Ticks check.
        if (++ticksInCombat != ((OnCombat) triggerBase).ticksInCombat) {
            return false;
        }

        return super.isValidTrigger(triggerBase, chargedItem);
    }
}
