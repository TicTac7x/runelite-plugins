package tictac7x.charges.item.listeners;

import net.runelite.api.HitsplatID;
import tictac7x.charges.events.CustomHitsplatApplied;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.utils.WeaponAttackStyle;
import tictac7x.charges.store.enums.HitsplatGroup;
import tictac7x.charges.store.enums.HitsplatTarget;

public class ListenerOnHitsplatApplied extends ListenerBase {
    private final WeaponAttackStyle weaponAttackStyle;

    public ListenerOnHitsplatApplied(final Provider provider, final ChargedItemBase chargedItem) {
        super(provider, chargedItem);

        this.weaponAttackStyle = new WeaponAttackStyle(provider.client);
    }

    public void trigger(final CustomHitsplatApplied event) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase, event)) continue;
            final OnHitsplatApplied trigger = (OnHitsplatApplied) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed && !trigger.multiTrigger) {
                // Once per game tick check.
                if (trigger.oncePerGameTick.isPresent()) {
                    trigger.triggerTick = provider.client.getTickCount();
                }

                return;
            }
        }
    }

    public boolean isValidTrigger(final TriggerBase triggerBase, final CustomHitsplatApplied event) {
        if (!(triggerBase instanceof OnHitsplatApplied)) return false;
        final OnHitsplatApplied trigger = (OnHitsplatApplied) triggerBase;

        // Hitsplat caused by other player check.
        if (!event.byMe) {
            return false;
        }

        // Hitsplat self check.
        if (trigger.hitsplatTarget == HitsplatTarget.SELF && !event.toMe) {
            return false;
        }

        // Hitsplat enemy check.
        if (trigger.hitsplatTarget == HitsplatTarget.ENEMY && event.toMe) {
            return false;
        }

        // All hitsplat check.
        if (trigger.hitsplatGroup == HitsplatGroup.ALL) {
            if (
                event.type != HitsplatID.DAMAGE_ME &&
                event.type != HitsplatID.DAMAGE_MAX_ME &&
                event.type != HitsplatID.BLOCK_ME
            ) {
                return false;
            }
        }

        // Hitsplat check.
        if (trigger.hitsplatGroup == HitsplatGroup.SUCCESSFUL) {
            if (
                event.type != HitsplatID.DAMAGE_ME &&
                event.type != HitsplatID.DAMAGE_MAX_ME
            ) {
                return false;
            }
        } else if (trigger.hitsplatGroup == HitsplatGroup.ALL) {
            if (
                event.type != HitsplatID.BLOCK_ME &&
                event.type != HitsplatID.DAMAGE_ME &&
                event.type != HitsplatID.DAMAGE_MAX_ME
            ) {
                return false;
            }
        } else if (trigger.hitsplatGroup == HitsplatGroup.BLOCKED && event.type != HitsplatID.BLOCK_ME) {
            return false;
        }

        // More than zero damage.
        if (trigger.moreThanZeroDamage.isPresent() && event.amount == 0) {
            return false;
        }

        // Name check.
        if (trigger.hasTargetName.isPresent()) {
            boolean nameCheck = false;
            for (final String name : trigger.hasTargetName.get()) {
                if (event.actor.getName() != null && event.actor.getName().equals(name)) {
                    nameCheck = true;
                    break;
                }
            }
            if (!nameCheck) {
                return false;
            }
        }

        // Once per game tick check.
        if (trigger.oncePerGameTick.isPresent() && provider.client.getTickCount() == trigger.triggerTick) {
            return false;
        }

        // Attack style check.
        if (trigger.combatStyle.isPresent() && weaponAttackStyle.getCombatStyle() != trigger.combatStyle.get()) {
            return false;
        }

        return super.isValidTrigger(trigger);
    }
}
