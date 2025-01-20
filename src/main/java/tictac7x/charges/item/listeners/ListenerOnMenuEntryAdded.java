package tictac7x.charges.item.listeners;

import net.runelite.api.Client;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.Notifier;
import net.runelite.client.game.ItemManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.ReplaceTarget;

import java.util.ArrayList;
import java.util.List;

public class ListenerOnMenuEntryAdded extends ListenerBase {
    public ListenerOnMenuEntryAdded(final Client client, final ItemManager itemManager, final ChargedItemBase chargedItem, final Notifier notifier, final TicTac7xChargesImprovedConfig config) {
        super(client, itemManager, chargedItem, notifier, config);
    }

    public void trigger(final MenuEntryAdded event) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase, event)) continue;
            final OnMenuEntryAdded trigger = (OnMenuEntryAdded) triggerBase;
            boolean triggerUsed = false;

            if (trigger.replaceOption.isPresent()) {
                event.getMenuEntry().setOption(trigger.replaceOption.get());
                triggerUsed = true;
            }

            if (trigger.replaceTargets.isPresent()) {
                for (final ReplaceTarget replaceTarget : trigger.replaceTargets.get()) {
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
                } catch (final Exception ignored) {}
                triggerUsed = true;
            }

            if (trigger.hide.isPresent() && trigger.menuEntryOption.isPresent()) {
                final List<MenuEntry> newMenuEntries = new ArrayList<>();

                for (final MenuEntry entry : client.getMenuEntries()) {
                    if (!entry.getOption().equals(trigger.menuEntryOption.get())) {
                        newMenuEntries.add(entry);
                    }
                }

                client.setMenuEntries(newMenuEntries.toArray(new MenuEntry[0]));
                triggerUsed = true;
            }

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final TriggerBase triggerBase, final MenuEntryAdded event) {
        if (!(triggerBase instanceof OnMenuEntryAdded)) return false;
        final OnMenuEntryAdded trigger = (OnMenuEntryAdded) triggerBase;

        // Check base triggers to avoid calling impostor id getters on client.
        impostorIdsTargetCheck: if (trigger.replaceImpostorIds.isPresent() && trigger.onMenuTarget.isPresent()) {
            for (final String target : trigger.onMenuTarget.get()) {
                if (event.getTarget().contains(target)) {
                    break impostorIdsTargetCheck;
                }
            }

            if (!super.isValidTrigger(trigger)) {
                return false;
            }
        }

        // Item id check.
        if (!trigger.replaceImpostorIds.isPresent() && event.getMenuEntry().getItemId() != chargedItem.itemId) {
            return false;
        }

        // Hide config check.
        if (trigger.hide.isPresent() && !config.hideDestroy()) {
            return false;
        }

        // Menu entry option check.
        if (trigger.menuEntryOption.isPresent() && !event.getOption().equals(trigger.menuEntryOption.get())) {
            return false;
        }

        // Menu option replace check.
        if (trigger.replaceOption.isPresent() && (
            !trigger.menuEntryOption.isPresent() ||
            !config.useCommonMenuEntries() ||
            !event.getOption().equals(trigger.menuEntryOption.get())
        )) {
            return false;
        }

        // Menu target replace check.
        menuReplaceTargetsCheck: if (trigger.replaceTargets.isPresent()) {
            for (final ReplaceTarget replaceTarget: trigger.replaceTargets.get()) {
                if (event.getTarget().contains(replaceTarget.target)) {
                    break menuReplaceTargetsCheck;
                }
            }

            return false;
        }

        // Menu replace impostor id check.
        replaceImpostorIdCheck: if (trigger.replaceImpostorIds.isPresent()) {
            for (final int impostorId : trigger.replaceImpostorIds.get()) {
                try {
                    if (client.getObjectDefinition(event.getMenuEntry().getIdentifier()).getImpostor().getId() == impostorId) {
                        break replaceImpostorIdCheck;
                    }
                } catch (final Exception ignored) {}
            }

            return false;
        }

        return true;
    }
}
