package tictac7x.charges.item.listeners;

import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;
import net.runelite.client.Notifier;
import net.runelite.client.game.ItemManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnGameTick;
import tictac7x.charges.item.triggers.OnXpDrop;
import tictac7x.charges.item.triggers.TriggerBase;

public class ListenerOnGameTick extends ListenerBase {
    public ListenerOnGameTick(final Client client, final ItemManager itemManager, final ChargedItemBase chargedItem, final Notifier notifier, final TicTac7xChargesImprovedConfig config) {
        super(client, itemManager, chargedItem, notifier, config);
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
