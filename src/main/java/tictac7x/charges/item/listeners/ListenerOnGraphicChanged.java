package tictac7x.charges.item.listeners;

import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ListenerOnGraphicChanged extends ListenerBase {
    public ListenerOnGraphicChanged(Provider provider) {
        super(provider);
    }

    public void trigger(CustomGraphicChanged event, ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;
            OnGraphicChanged trigger = (OnGraphicChanged) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) {
                afterTrigger(trigger);
                return;
            }
        }
    }

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, CustomGraphicChanged event) {
        if (!(triggerBase instanceof OnGraphicChanged)) return false;
        OnGraphicChanged trigger = (OnGraphicChanged) triggerBase;

        // Graphic id check.
        graphicIdCheck: if (trigger.graphicId != null) {
            for (int graphicId : trigger.graphicId) {
                if (event.hasGraphicId(graphicId)) {
                    break graphicIdCheck;
                }
            }

            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
