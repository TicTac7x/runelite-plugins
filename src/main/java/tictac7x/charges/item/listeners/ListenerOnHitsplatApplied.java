package tictac7x.charges.item.listeners;

import net.runelite.api.*;
import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.enums.*;
import tictac7x.charges.store.utils.*;

public class ListenerOnHitsplatApplied extends ListenerBase {
    private WeaponAttackStyle weaponAttackStyle;

    public ListenerOnHitsplatApplied(Provider provider) {
        super(provider);

        this.weaponAttackStyle = new WeaponAttackStyle(provider.client);
    }

    public void trigger(CustomHitsplatApplied event, ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;
            OnHitsplatApplied trigger = (OnHitsplatApplied) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger, chargedItem)) {
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

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, CustomHitsplatApplied event) {
        if (!(triggerBase instanceof OnHitsplatApplied)) return false;
        OnHitsplatApplied trigger = (OnHitsplatApplied) triggerBase;

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

        if (trigger.hitsplatGroup == HitsplatGroup.DISEASE_BLOCKED && event.type != HitsplatID.DISEASE_BLOCKED) {
            return false;
        }

        // More than zero damage.
        if (trigger.moreThanZeroDamage.isPresent() && event.amount == 0) {
            return false;
        }

        // Name check.
        if (trigger.hasTargetName.isPresent()) {
            boolean nameCheck = false;
            for (String name : trigger.hasTargetName.get()) {
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

        return super.isValidTrigger(trigger, chargedItem);
    }
}
