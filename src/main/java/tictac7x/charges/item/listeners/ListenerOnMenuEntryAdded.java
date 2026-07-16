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

            if (trigger.replaceTargets.isPresent()) {
                for (ReplaceTarget replaceTarget : trigger.replaceTargets.get()) {
                    if (event.getTarget().contains(replaceTarget.target)) {
                        event.getMenuEntry().setTarget(event.getTarget().replaceAll(replaceTarget.target, replaceTarget.replace));
                        triggerUsed = true;
                        break;
                    }
                }
            }

            if (trigger.replaceTargetDynamically.isPresent() && event.getTarget().contains(trigger.replaceTargetDynamically.get().target)) {
                try {
                    event.getMenuEntry().setTarget(event.getTarget().replaceAll(trigger.replaceTargetDynamically.get().target, trigger.replaceTargetDynamically.get().replace.call()));
                } catch (Exception ignored) {}
                triggerUsed = true;
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

            if (triggerUsed) {
                afterTrigger(trigger);
                return;
            }
        }
    }

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, MenuEntryAdded event) {
        if (!(triggerBase instanceof OnMenuEntryAdded)) return false;
        OnMenuEntryAdded trigger = (OnMenuEntryAdded) triggerBase;

        // Check base triggers to avoid calling impostor id getters on client.
        impostorIdsTargetCheck: if (trigger.replaceImpostorIds.isPresent() && trigger.onMenuTarget.isPresent()) {
            for (String target : trigger.onMenuTarget.get()) {
                if (event.getTarget().contains(target)) {
                    break impostorIdsTargetCheck;
                }
            }

            if (!super.isValidTrigger(trigger, chargedItem)) {
                return false;
            }
        }

        // Item id check.
        if (!trigger.replaceImpostorIds.isPresent()) {
            boolean idCheck = false;

            for (TriggerItem item : chargedItem.items) {
                if (item.itemId == event.getMenuEntry().getItemId()) {
                    idCheck = true;
                    break;
                }
            }

            if (!idCheck) {
                return false;
            }
        }

        // Hide config check.
        if (trigger.hide.isPresent() && !provider.config.hideDestroyMenuEntries()) {
            return false;
        }

        // Menu entry option check.
        if (trigger.menuEntryOption.isPresent() && !event.getOption().equals(trigger.menuEntryOption.get())) {
            return false;
        }

        // Menu target replace check.
        menuReplaceTargetsCheck: if (trigger.replaceTargets.isPresent()) {
            for (ReplaceTarget replaceTarget: trigger.replaceTargets.get()) {
                if (event.getTarget().contains(replaceTarget.target)) {
                    break menuReplaceTargetsCheck;
                }
            }

            return false;
        }

        // Menu replace impostor id check.
        replaceImpostorIdCheck: if (trigger.replaceImpostorIds.isPresent()) {
            for (int impostorId : trigger.replaceImpostorIds.get()) {
                try {
                    if (provider.client.getObjectDefinition(event.getMenuEntry().getIdentifier()).getImpostor().getId() == impostorId) {
                        break replaceImpostorIdCheck;
                    }
                } catch (Exception ignored) {}
            }

            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
