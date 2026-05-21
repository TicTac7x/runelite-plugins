package tictac7x.charges.item.listeners;

import net.runelite.api.events.GameTick;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnGameTick;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnGameTick extends ListenerBase {
    public ListenerOnGameTick(final Provider provider) {
        super(provider);
    }

    public void trigger(final GameTick gameTick, final ChargedItemBase chargedItem) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, gameTick)) continue;
            final OnGameTick trigger = (OnGameTick) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final ChargedItemBase chargedItem, final TriggerBase triggerBase, final GameTick event) {
        if (!(triggerBase instanceof OnGameTick)) return false;
        final OnGameTick trigger = (OnGameTick) triggerBase;

        return super.isValidTrigger(trigger, chargedItem);
    }
}
