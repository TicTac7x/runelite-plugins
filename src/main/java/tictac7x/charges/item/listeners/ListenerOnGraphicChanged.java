package tictac7x.charges.item.listeners;

import net.runelite.api.events.GraphicChanged;
import tictac7x.charges.events.CustomGraphicChanged;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnGraphicChanged extends ListenerBase {
    public ListenerOnGraphicChanged(final Provider provider) {
        super(provider);
    }

    public void trigger(final CustomGraphicChanged event, final ChargedItemBase chargedItem) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;
            final OnGraphicChanged trigger = (OnGraphicChanged) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final ChargedItemBase chargedItem, final TriggerBase triggerBase, final CustomGraphicChanged event) {
        if (!(triggerBase instanceof OnGraphicChanged)) return false;
        final OnGraphicChanged trigger = (OnGraphicChanged) triggerBase;

        // Graphic id check.
        graphicIdCheck: if (trigger.graphicId != null) {
            for (final int graphicId : trigger.graphicId) {
                if (event.hasGraphicId(graphicId)) {
                    break graphicIdCheck;
                }
            }

            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
