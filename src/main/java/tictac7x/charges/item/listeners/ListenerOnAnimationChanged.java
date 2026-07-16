package tictac7x.charges.item.listeners;

import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class ListenerOnAnimationChanged extends ListenerBase {
    public ListenerOnAnimationChanged(Provider provider) {
        super(provider);
    }

    public void trigger(CustomAnimationChanged event, ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;

            OnAnimationChanged trigger = (OnAnimationChanged) triggerBase;
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

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, CustomAnimationChanged event) {
        if (!(triggerBase instanceof OnAnimationChanged)) return false;
        OnAnimationChanged trigger = (OnAnimationChanged) triggerBase;

        // Actor name check.
        if (trigger.actorName.isPresent()) {
            if (!Objects.equals(event.actor.getName(), trigger.actorName.get())) {
                return false;
            }
        // Player check.
        } else if (event.actor != provider.client.getLocalPlayer()) {
            return false;
        }

        // Animation id check.
        animationIdCheck: if (trigger.animationId != null) {
            for (int animationId : trigger.animationId) {
                if (event.actor.getAnimation() == animationId) {
                    break animationIdCheck;
                }
            }

            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
