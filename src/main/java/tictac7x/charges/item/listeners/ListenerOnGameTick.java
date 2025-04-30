package tictac7x.charges.item.listeners;

import net.runelite.api.events.GameTick;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnGameTick;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnGameTick extends ListenerBase {
    public ListenerOnGameTick(final Provider provider, final ChargedItemBase chargedItem) {
        super(provider, chargedItem);
    }

    public void trigger(final GameTick gameTick) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase, gameTick)) continue;
            final OnGameTick trigger = (OnGameTick) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final TriggerBase triggerBase, final GameTick event) {
        if (!(triggerBase instanceof OnGameTick)) return false;
        final OnGameTick trigger = (OnGameTick) triggerBase;

        return super.isValidTrigger(trigger);
    }
}
