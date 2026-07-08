package tictac7x.charges.item.triggers;

import tictac7x.charges.events.CustomScriptPreFired;

import java.util.Optional;
import java.util.function.Consumer;

public class OnScriptPreFired extends TriggerBase {
    public final int scriptId;
    public Optional<Consumer<CustomScriptPreFired>> scriptConsumer = Optional.empty();

    public OnScriptPreFired(final int scriptId) {
        this.scriptId = scriptId;
    }

    public OnScriptPreFired scriptConsumer(final Consumer<CustomScriptPreFired> consumer) {
        this.scriptConsumer = Optional.of(consumer);
        return this;
    }
}
