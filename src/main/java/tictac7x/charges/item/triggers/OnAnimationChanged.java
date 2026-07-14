package tictac7x.charges.item.triggers;

import java.util.*;

public class OnAnimationChanged extends TriggerBase {
    public int[] animationId;
    public Optional<String> actorName = Optional.empty();

    public OnAnimationChanged(int ...animationId) {
        this.animationId = animationId;
    }

    public OnAnimationChanged actorName(String actorName) {
        if (actorName == null) {
            this.actorName = Optional.of("null");
        } else {
            this.actorName = Optional.of(actorName);
        }
        return this;
    }
}
