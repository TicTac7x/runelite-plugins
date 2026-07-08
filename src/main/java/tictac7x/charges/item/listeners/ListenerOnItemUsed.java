package tictac7x.charges.item.listeners;

import tictac7x.charges.events.CustomMenuOptionClicked;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnItemUsed;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnItemUsed extends ListenerBase {
    public ListenerOnItemUsed(final Provider provider) {
        super(provider);
    }

    public void trigger(final CustomMenuOptionClicked event, final ChargedItemBase chargedItem) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;

            final OnItemUsed triggerOnItemUsed = (OnItemUsed) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(triggerOnItemUsed, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    private boolean isValidTrigger(final ChargedItemBase chargedItem, final TriggerBase triggerBase, final CustomMenuOptionClicked event) {
        if (!(triggerBase instanceof OnItemUsed)) return false;
        if (event.usedItemId.isEmpty()) return false;
        final OnItemUsed triggerOnItemUsed = (OnItemUsed) triggerBase;

        if (!(
            (event.itemId == triggerOnItemUsed.targetItemId && event.usedItemId.get() == triggerOnItemUsed.usedItemId) ||
            (triggerOnItemUsed.isBothWays && event.itemId == event.usedItemId.get() && event.usedItemId.get() == triggerOnItemUsed.targetItemId)
        )) {
            return false;
        }

        return super.isValidTrigger(triggerBase, chargedItem);
    }
}
