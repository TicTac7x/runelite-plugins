package tictac7x.charges.item.triggers;

import tictac7x.charges.events.*;

import java.util.*;
import java.util.function.*;

public class OnScriptPreFired extends TriggerBase {
    public int scriptId;
    public Optional<Consumer<CustomScriptPreFired>> scriptConsumer = Optional.empty();

    public OnScriptPreFired(int scriptId) {
        this.scriptId = scriptId;
    }

    public OnScriptPreFired scriptConsumer(Consumer<CustomScriptPreFired> consumer) {
        this.scriptConsumer = Optional.of(consumer);
        return this;
    }
}
