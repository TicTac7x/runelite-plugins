package tictac7x.charges.item.triggers;

import java.util.*;
import java.util.function.*;

public class OnVarbitChanged extends TriggerBase {
    public int varbitId;

    public Optional<Integer> varbitValue = Optional.empty();
    public Optional<Consumer<Integer>> varbitValueConsumer = Optional.empty();
    public Optional<Boolean> setDynamically = Optional.empty();

    public OnVarbitChanged(int varbitId) {
        this.varbitId = varbitId;
    }

    public OnVarbitChanged(int varbitId, int varbitValue) {
        this.varbitId = varbitId;
        this.varbitValue = Optional.of(varbitValue);
    }

    public OnVarbitChanged varbitValueConsumer(Consumer<Integer> consumer) {
        this.varbitValueConsumer = Optional.of(consumer);
        return this;
    }

    public OnVarbitChanged setDynamically() {
        this.setDynamically = Optional.of(true);
        return this;
    }
}
