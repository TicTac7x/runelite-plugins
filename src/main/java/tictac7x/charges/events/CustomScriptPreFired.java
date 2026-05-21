package tictac7x.charges.events;

import net.runelite.api.ScriptEvent;
import net.runelite.api.events.ScriptPreFired;
import net.runelite.api.widgets.Widget;

import java.util.Optional;

public class CustomScriptPreFired {
    public final int scriptId;
    public final Object[] arguments;
    final ScriptEvent scriptEvent;

    public CustomScriptPreFired(final ScriptPreFired event) {
        this.scriptId = event.getScriptId();
        this.arguments = event.getScriptEvent().getArguments();
        this.scriptEvent = event.getScriptEvent();
    }

    @Override
    public String toString() {
        String scriptDebug = "SCRIPT FIRED | script id: " + scriptId;
		try {
			final Optional<Widget> widget = Optional.ofNullable(scriptEvent.getSource());
			if (widget.isPresent()) {
				scriptDebug += ", widget id: " + widget.get().getId();
			}
		} catch (final Exception ignored) {}

        try {
			String arguments = ", arguments: [";
			for (final Object argument : scriptEvent.getArguments()) {
				arguments += argument + ", ";
			}
			arguments += "]";
			scriptDebug += arguments.replaceAll(", ]", "]");
		} catch (final Exception ignored) {}

        return scriptDebug;
    }
}
