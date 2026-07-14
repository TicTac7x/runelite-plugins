package tictac7x.charges.item.listeners;

import net.runelite.api.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ListenerOnGameTick extends ListenerBase {
    public ListenerOnGameTick(Provider provider) {
        super(provider);
    }

    public void trigger(GameTick gameTick, ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, gameTick)) continue;
            OnGameTick trigger = (OnGameTick) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, GameTick event) {
        if (!(triggerBase instanceof OnGameTick)) return false;
        OnGameTick trigger = (OnGameTick) triggerBase;

        return super.isValidTrigger(trigger, chargedItem);
    }
}
