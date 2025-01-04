package tictac7x.charges.item.listeners;

import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.events.ScriptPreFired;
import net.runelite.api.events.StatChanged;
import net.runelite.client.Notifier;
import net.runelite.client.game.ItemManager;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnScriptPreFired;
import tictac7x.charges.item.triggers.OnStatChanged;
import tictac7x.charges.item.triggers.TriggerBase;

public class ListenerOnScriptPreFired extends ListenerBase {
    public ListenerOnScriptPreFired(final Client client, final ItemManager itemManager, final ChargedItemBase chargedItem, final Notifier notifier, final ChargesImprovedConfig config) {
        super(client, itemManager, chargedItem, notifier, config);
    }

    public void trigger(final ScriptPreFired event) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase, event)) continue;
            final OnScriptPreFired trigger = (OnScriptPreFired) triggerBase;
            boolean triggerUsed = false;

            if (trigger.scriptConsumer.isPresent()) {
                trigger.scriptConsumer.get().accept(event);
                triggerUsed = true;
            }

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final TriggerBase triggerBase, final ScriptPreFired event) {
        if (!(triggerBase instanceof OnScriptPreFired)) return false;
        final OnScriptPreFired trigger = (OnScriptPreFired) triggerBase;

        // Id check.
        if (trigger.scriptId != event.getScriptId()) {
            return false;
        }

        return super.isValidTrigger(trigger);
    }
}
