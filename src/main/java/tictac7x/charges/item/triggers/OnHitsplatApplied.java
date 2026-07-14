package tictac7x.charges.item.triggers;

import tictac7x.charges.store.enums.*;

import java.util.Optional;

public class OnHitsplatApplied extends TriggerBase {
    public HitsplatTarget hitsplatTarget;
    public HitsplatGroup hitsplatGroup;

    public Optional<Boolean> moreThanZeroDamage = Optional.empty();
    public Optional<String[]> hasTargetName = Optional.empty();
    public Optional<Boolean> oncePerGameTick = Optional.empty();
    public Optional<CombatStyle> combatStyle = Optional.empty();
    public int triggerTick = 0;

    public OnHitsplatApplied(HitsplatTarget hitsplatTarget, HitsplatGroup hitsplatGroup) {
        this.hitsplatTarget = hitsplatTarget;
        this.hitsplatGroup = hitsplatGroup;
    }

    public OnHitsplatApplied moreThanZeroDamage() {
        this.moreThanZeroDamage = Optional.of(true);
        return this;
    }

    public OnHitsplatApplied hasTargetName(String ...name) {
        this.hasTargetName = Optional.of(name);
        return this;
    }

    public OnHitsplatApplied oncePerGameTick() {
        this.oncePerGameTick = Optional.of(true);
        return this;
    }

    public OnHitsplatApplied combatStyle(CombatStyle combatStyle) {
        this.combatStyle = Optional.of(combatStyle);
        return this;
    }
}
