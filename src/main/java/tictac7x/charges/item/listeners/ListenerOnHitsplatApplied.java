package tictac7x.charges.item.listeners;

import net.runelite.api.Client;
import net.runelite.api.Hitsplat;
import net.runelite.api.HitsplatID;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.client.Notifier;
import net.runelite.client.game.ItemManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.HitsplatGroup;
import tictac7x.charges.store.HitsplatTarget;

public class ListenerOnHitsplatApplied extends ListenerBase {
    public ListenerOnHitsplatApplied(final Client client, final ItemManager itemManager, final ChargedItemBase chargedItem, final Notifier notifier, final TicTac7xChargesImprovedConfig config) {
        super(client, itemManager, chargedItem, notifier, config);
    }

    public void trigger(final HitsplatApplied event) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase, event)) continue;
            final OnHitsplatApplied trigger = (OnHitsplatApplied) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final TriggerBase triggerBase, final HitsplatApplied event) {
        if (!(triggerBase instanceof OnHitsplatApplied)) return false;
        final OnHitsplatApplied trigger = (OnHitsplatApplied) triggerBase;
        final Hitsplat hitsplat = event.getHitsplat();

        // Hitsplat caused by other player check.
        if (event.getHitsplat().isOthers()) {
            return false;
        }

        // Hitsplat self check.
        if (trigger.hitsplatTarget == HitsplatTarget.SELF && event.getActor() != client.getLocalPlayer()) {
            return false;
        }

        // Hitsplat enemy check.
        if (trigger.hitsplatTarget == HitsplatTarget.ENEMY && event.getActor() == client.getLocalPlayer()) {
            return false;
        }

        // Hitsplay group check.
        if (trigger.hitsplatGroup == HitsplatGroup.REGULAR && (
            hitsplat.getHitsplatType() != HitsplatID.DAMAGE_ME &&
            hitsplat.getHitsplatType() != HitsplatID.DAMAGE_MAX_ME
        )) {
            return false;
        }

        // More than zero damage.
        if (trigger.moreThanZeroDamage.isPresent() && hitsplat.getAmount() == 0) {
            return false;
        }

        return super.isValidTrigger(trigger);
    }
}
