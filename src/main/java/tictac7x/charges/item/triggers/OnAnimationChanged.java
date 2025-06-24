package tictac7x.charges.item.triggers;

import java.util.Optional;

public class OnAnimationChanged extends TriggerBase {
    public final int[] animationId;
    public Optional<String> actorName = Optional.empty();

    public OnAnimationChanged(final int ...animationId) {
        this.animationId = animationId;
    }

    public OnAnimationChanged actorName(final String actorName) {
        if (actorName == null) {
            this.actorName = Optional.of("null");
        } else {
            this.actorName = Optional.of(actorName);
        }
        return this;
    }
}
