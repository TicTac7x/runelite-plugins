package tictac7x.charges.item.listeners;

import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ListenerOnItemUsed extends ListenerBase {
    public ListenerOnItemUsed(Provider provider) {
        super(provider);
    }

    public void trigger(CustomMenuOptionClicked event, ChargedItemBase chargedItem) {
        for (TriggerBase trigger : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, trigger, event)) continue;

            OnItemUsed triggerOnItemUsed = (OnItemUsed) trigger;
            boolean triggerUsed = false;

            if (super.trigger(triggerOnItemUsed, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) {
                afterTrigger(trigger);
                return;
            }
        }
    }

    private boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, CustomMenuOptionClicked event) {
        if (!(triggerBase instanceof OnItemUsed)) return false;
        if (event.usedItemId.isEmpty()) return false;
        OnItemUsed triggerOnItemUsed = (OnItemUsed) triggerBase;

        if (!(
            (event.itemId == triggerOnItemUsed.targetItemId && event.usedItemId.get() == triggerOnItemUsed.usedItemId) ||
            (triggerOnItemUsed.isBothWays && event.itemId == event.usedItemId.get() && event.usedItemId.get() == triggerOnItemUsed.targetItemId)
        )) {
            return false;
        }

        return super.isValidTrigger(triggerBase, chargedItem);
    }
}
