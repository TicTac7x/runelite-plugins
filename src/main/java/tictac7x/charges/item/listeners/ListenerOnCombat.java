package tictac7x.charges.item.listeners;

import net.runelite.api.Client;
import net.runelite.client.Notifier;
import net.runelite.client.game.ItemManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnCombat;
import tictac7x.charges.item.triggers.TriggerBase;

public class ListenerOnCombat extends ListenerBase {
    private int ticksInCombat = 0;

    public ListenerOnCombat(final Client client, final ItemManager itemManager, final ChargedItemBase chargedItem, final Notifier notifier, final TicTac7xChargesImprovedConfig config) {
        super(client, itemManager, chargedItem, notifier, config);
    }

    public void trigger() {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase)) continue;
            final OnCombat trigger = (OnCombat) triggerBase;
            boolean triggerUsed = false;

            if (trigger.ticksInCombat == ticksInCombat) {
                triggerUsed = true;
                ticksInCombat = 0;
            }

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final TriggerBase triggerBase) {
        if (!(triggerBase instanceof OnCombat)) return false;

        // Ticks check.
        if (++ticksInCombat != ((OnCombat) triggerBase).ticksInCombat) {
            return false;
        }

        return super.isValidTrigger(triggerBase);
    }
}
