package tictac7x.charges.item.listeners;

import net.runelite.api.*;
import net.runelite.api.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.utils.*;

import java.util.*;

public class ListenerOnMenuEntryAdded extends ListenerBase {
    public ListenerOnMenuEntryAdded(Provider provider) {
        super(provider);
    }

    public void trigger(MenuEntryAdded event, ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) {
                continue;
            };
            OnMenuEntryAdded trigger = (OnMenuEntryAdded) triggerBase;
            boolean triggerUsed = false;

            if (trigger.replaceOption.isPresent()) {
                event.getMenuEntry().setOption(trigger.replaceOption.get());
                triggerUsed = true;
            }

            if (trigger.replaceOptionConsumer.isPresent()) {
                try {
                    event.getMenuEntry().setOption(trigger.replaceOptionConsumer.get().call());
                    triggerUsed = true;
                } catch (Exception ignored) {}
            }

            if (trigger.hide.isPresent() && trigger.menuEntryOption.isPresent()) {
                List<MenuEntry> newMenuEntries = new ArrayList<>();

                for (MenuEntry entry : provider.client.getMenuEntries()) {
                    if (!entry.getOption().equals(trigger.menuEntryOption.get())) {
                        newMenuEntries.add(entry);
                    }
                }

                provider.client.setMenuEntries(newMenuEntries.toArray(new MenuEntry[0]));
                triggerUsed = true;
            }

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, MenuEntryAdded event) {
        if (!(triggerBase instanceof OnMenuEntryAdded)) return false;
        OnMenuEntryAdded trigger = (OnMenuEntryAdded) triggerBase;

        // Menu item id check
        if (event.getItemId() != chargedItem.itemId) {
            return false;
        }

        // Hide config check.
        if (trigger.hide.isPresent() && !provider.config.hideDestroyMenuEntries()) {
            return false;
        }

        // Menu entry option check.
        if (trigger.menuEntryOption.isPresent() && !event.getOption().equals(trigger.menuEntryOption.get())) {
            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
