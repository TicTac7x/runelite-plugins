package tictac7x.charges.item.listeners;

import net.runelite.api.events.VarbitChanged;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.triggers.OnVarbitsMapChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnVarbitsMapChanged extends ListenerBase {
    public ListenerOnVarbitsMapChanged(final Provider provider) {
        super(provider);
    }

    public void trigger(final VarbitChanged event, final ChargedItemBase chargedItemBase) {
        for (final TriggerBase triggerBase : chargedItemBase.triggers) {
            if (!isValidTrigger(chargedItemBase, triggerBase, event)) continue;
            final OnVarbitsMapChanged trigger = (OnVarbitsMapChanged) triggerBase;
            final ChargedItemWithStorage chargedItem = (ChargedItemWithStorage) chargedItemBase;

            chargedItem.storage.put(trigger.varbitsMap.get(event.getVarbitId()), event.getValue());
            return;
        }
    }

    public boolean isValidTrigger(final ChargedItemBase chargedItem, final TriggerBase triggerBase, final VarbitChanged event) {
        if (!(triggerBase instanceof OnVarbitsMapChanged)) return false;
        if (!(chargedItem instanceof ChargedItemWithStorage)) return false;

        final OnVarbitsMapChanged trigger = (OnVarbitsMapChanged) triggerBase;

        // Valid varbit id check.
        if (!trigger.varbitsMap.containsKey(event.getVarbitId())) {
            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
