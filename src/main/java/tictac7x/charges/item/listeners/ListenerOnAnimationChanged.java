package tictac7x.charges.item.listeners;

import net.runelite.api.events.AnimationChanged;
import tictac7x.charges.events.CustomAnimationChanged;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnAnimationChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

import java.util.Objects;

public class ListenerOnAnimationChanged extends ListenerBase {
    public ListenerOnAnimationChanged(final Provider provider) {
        super(provider);
    }

    public void trigger(final CustomAnimationChanged event, final ChargedItemBase chargedItem) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;

            final OnAnimationChanged trigger = (OnAnimationChanged) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final ChargedItemBase chargedItem, final TriggerBase triggerBase, final CustomAnimationChanged event) {
        if (!(triggerBase instanceof OnAnimationChanged)) return false;
        final OnAnimationChanged trigger = (OnAnimationChanged) triggerBase;

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
            for (final int animationId : trigger.animationId) {
                if (event.actor.getAnimation() == animationId) {
                    break animationIdCheck;
                }
            }

            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
