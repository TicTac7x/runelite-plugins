package tictac7x.charges.item.listeners;

import net.runelite.api.events.AnimationChanged;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnAnimationChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

import java.util.Objects;

public class ListenerOnAnimationChanged extends ListenerBase {
    public ListenerOnAnimationChanged(final Provider provider, final ChargedItemBase chargedItem) {
        super(provider, chargedItem);
    }

    public void trigger(final AnimationChanged event) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase, event)) continue;

            final OnAnimationChanged trigger = (OnAnimationChanged) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final TriggerBase triggerBase, final AnimationChanged event) {
        if (!(triggerBase instanceof OnAnimationChanged)) return false;
        final OnAnimationChanged trigger = (OnAnimationChanged) triggerBase;

        // Actor name check.
        if (trigger.actorName.isPresent()) {
            if (!Objects.equals(event.getActor().getName(), trigger.actorName.get())) {
                return false;
            }
        // Player check.
        } else if (event.getActor() != provider.client.getLocalPlayer()) {
            return false;
        }

        // Animation id check.
        animationIdCheck: if (trigger.animationId != null) {
            for (final int animationId : trigger.animationId) {
                if (event.getActor().getAnimation() == animationId) {
                    break animationIdCheck;
                }
            }

            return false;
        }

        return super.isValidTrigger(trigger);
    }
}
