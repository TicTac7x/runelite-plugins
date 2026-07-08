package tictac7x.charges.item.listeners;

import net.runelite.api.Skill;
import net.runelite.api.events.StatChanged;
import tictac7x.charges.events.CustomStatChanged;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnXpDrop;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnXpDrop extends ListenerBase {
    public ListenerOnXpDrop(final Provider provider) {
        super(provider);
    }

    public void trigger(final CustomStatChanged event, final ChargedItemBase chargedItem) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;
            final OnXpDrop trigger = (OnXpDrop) triggerBase;
            boolean triggerUsed = false;

            if (trigger.xpAmountConsumer.isPresent()) {
                trigger.xpAmountConsumer.get().accept(event.xpDrop);
                triggerUsed = true;
            }

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final ChargedItemBase chargedItem, final TriggerBase triggerBase, final CustomStatChanged event) {
        if (!(triggerBase instanceof OnXpDrop)) return false;
        final OnXpDrop trigger = (OnXpDrop) triggerBase;

        // Skill check.
        if (trigger.skill != event.skill) {
            return false;
        }

        // XP drop check.
        if (event.xpDrop == 0) {
            return false;
        }

        // Amount check.
        if (trigger.amount.isPresent() && trigger.amount.get() != event.xpDrop
        ) {
            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
