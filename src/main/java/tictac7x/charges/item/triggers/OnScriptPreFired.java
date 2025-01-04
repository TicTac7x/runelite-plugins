package tictac7x.charges.item.triggers;

import net.runelite.api.Skill;
import net.runelite.api.events.ScriptPreFired;

import java.util.Optional;
import java.util.function.Consumer;

public class OnScriptPreFired extends TriggerBase {
    public final int scriptId;
    public Optional<Consumer<ScriptPreFired>> scriptConsumer = Optional.empty();

    public OnScriptPreFired(final int scriptId) {
        this.scriptId = scriptId;
    }

    public OnScriptPreFired scriptConsumer(final Consumer<ScriptPreFired> consumer) {
        this.scriptConsumer = Optional.of(consumer);
        return this;
    }
}
