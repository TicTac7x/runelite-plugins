package tictac7x.charges.item.listeners;

import tictac7x.charges.events.CustomMenuOptionClicked;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnItemUsed;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnItemUsed extends ListenerBase {
    public ListenerOnItemUsed(final Provider provider, final ChargedItemBase chargedItem) {
        super(provider, chargedItem);
    }

    public void trigger(final CustomMenuOptionClicked event) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase, event)) continue;

            final OnItemUsed triggerOnItemUsed = (OnItemUsed) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(triggerOnItemUsed)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    private boolean isValidTrigger(final TriggerBase triggerBase, final CustomMenuOptionClicked event) {
        if (!(triggerBase instanceof OnItemUsed)) return false;
        if (event.usedItemId.isEmpty()) return false;
        final OnItemUsed triggerOnItemUsed = (OnItemUsed) triggerBase;

        return (
            event.itemId == triggerOnItemUsed.targetItemId && event.usedItemId.get() == triggerOnItemUsed.usedItemId ||
            triggerOnItemUsed.isBothWays && event.itemId == event.usedItemId.get() && event.usedItemId.get() == triggerOnItemUsed.targetItemId
        );
    }
}
