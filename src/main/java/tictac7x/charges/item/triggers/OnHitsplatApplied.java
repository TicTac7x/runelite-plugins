package tictac7x.charges.item.triggers;

import tictac7x.charges.store.HitsplatGroup;
import tictac7x.charges.store.HitsplatTarget;

import java.util.Optional;

public class OnHitsplatApplied extends TriggerBase {
    public final HitsplatTarget hitsplatTarget;
    public HitsplatGroup hitsplatGroup = HitsplatGroup.REGULAR;

    public Optional<Boolean> moreThanZeroDamage = Optional.empty();
    public Optional<String> hasTargetName = Optional.empty();

    public OnHitsplatApplied(final HitsplatTarget hitsplatTarget) {
        this.hitsplatTarget = hitsplatTarget;
    }

    public OnHitsplatApplied moreThanZeroDamage() {
        this.moreThanZeroDamage = Optional.of(true);
        return this;
    }

    public OnHitsplatApplied hasTargetName(final String name) {
        this.hasTargetName = Optional.of(name);
        return this;
    }
}
