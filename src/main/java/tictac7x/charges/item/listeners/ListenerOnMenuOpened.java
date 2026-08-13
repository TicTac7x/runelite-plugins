package tictac7x.charges.item.listeners;

import net.runelite.api.*;
import net.runelite.api.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ListenerOnMenuOpened extends ListenerBase {
    public ListenerOnMenuOpened(Provider provider) {
        super(provider);
    }

    public void trigger(MenuOpened menuOpened, ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, menuOpened)) continue;
            OnMenuOpened trigger = (OnMenuOpened) triggerBase;
            boolean triggerUsed = false;

            if (trigger.menuConsumer.isPresent()) {
                triggerUsed = true;
                trigger.menuConsumer.get().accept(menuOpened);
            }

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, MenuOpened menuOpened) {
        if (!(triggerBase instanceof OnMenuOpened)) return false;

        // Item check.
        boolean itemCheck = false;
        for (MenuEntry menuEntry : menuOpened.getMenuEntries()) {
            if (menuEntry.getItemId() == chargedItem.itemId) {
                itemCheck = true;
                break;
            }
        }
        if (!itemCheck) return false;

        return super.isValidTrigger(triggerBase, chargedItem);
    }
}
