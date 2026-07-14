package tictac7x.charges.item.listeners;

import net.runelite.api.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ListenerOnVarbitsMapChanged extends ListenerBase {
    public ListenerOnVarbitsMapChanged(Provider provider) {
        super(provider);
    }

    public void trigger(VarbitChanged event, ChargedItemBase chargedItemBase) {
        for (TriggerBase triggerBase : chargedItemBase.triggers) {
            if (!isValidTrigger(chargedItemBase, triggerBase, event)) continue;
            OnVarbitsMapChanged trigger = (OnVarbitsMapChanged) triggerBase;
            ChargedItemWithStorage chargedItem = (ChargedItemWithStorage) chargedItemBase;

            chargedItem.storage.put(trigger.varbitsMap.get(event.getVarbitId()), event.getValue());
            return;
        }
    }

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, VarbitChanged event) {
        if (!(triggerBase instanceof OnVarbitsMapChanged)) return false;
        if (!(chargedItem instanceof ChargedItemWithStorage)) return false;

        OnVarbitsMapChanged trigger = (OnVarbitsMapChanged) triggerBase;

        // Valid varbit id check.
        if (!trigger.varbitsMap.containsKey(event.getVarbitId())) {
            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
