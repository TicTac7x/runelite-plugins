package tictac7x.charges.item.triggers;

import tictac7x.charges.store.enums.CombatStyle;
import tictac7x.charges.store.enums.HitsplatGroup;
import tictac7x.charges.store.enums.HitsplatTarget;

import java.util.Optional;

public class OnHitsplatApplied extends TriggerBase {
    public final HitsplatTarget hitsplatTarget;
    public final HitsplatGroup hitsplatGroup;

    public Optional<Boolean> moreThanZeroDamage = Optional.empty();
    public Optional<String[]> hasTargetName = Optional.empty();
    public Optional<Boolean> oncePerGameTick = Optional.empty();
    public Optional<CombatStyle> combatStyle = Optional.empty();
    public int triggerTick = 0;

    public OnHitsplatApplied(final HitsplatTarget hitsplatTarget, final HitsplatGroup hitsplatGroup) {
        this.hitsplatTarget = hitsplatTarget;
        this.hitsplatGroup = hitsplatGroup;
    }

    public OnHitsplatApplied moreThanZeroDamage() {
        this.moreThanZeroDamage = Optional.of(true);
        return this;
    }

    public OnHitsplatApplied hasTargetName(final String ...name) {
        this.hasTargetName = Optional.of(name);
        return this;
    }

    public OnHitsplatApplied oncePerGameTick() {
        this.oncePerGameTick = Optional.of(true);
        return this;
    }

    public OnHitsplatApplied combatStyle(final CombatStyle combatStyle) {
        this.combatStyle = Optional.of(combatStyle);
        return this;
    }
}
