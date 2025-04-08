package tictac7x.charges.item.triggers;

import java.util.Optional;

public class OnCombat extends TriggerBase {
    public final int ticksInCombat;

    public OnCombat(final int ticksInCombat) {
        this.ticksInCombat = ticksInCombat;
    }
}
