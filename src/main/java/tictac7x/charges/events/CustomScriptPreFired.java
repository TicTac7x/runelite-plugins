package tictac7x.charges.events;

import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.*;

import java.util.Optional;

public class CustomScriptPreFired {
    public int scriptId;
    public Object[] arguments;
    ScriptEvent scriptEvent;

    public CustomScriptPreFired(ScriptPreFired event) {
        this.scriptId = event.getScriptId();
        this.arguments = event.getScriptEvent().getArguments();
        this.scriptEvent = event.getScriptEvent();
    }

    @Override
    public String toString() {
        String scriptDebug = "SCRIPT FIRED | script id: " + scriptId;
		try {
			Optional<Widget> widget = Optional.ofNullable(scriptEvent.getSource());
			if (widget.isPresent()) {
				scriptDebug += ", widget id: " + widget.get().getId();
			}
		} catch (Exception ignored) {}

        try {
			String arguments = ", arguments: [";
			for (Object argument : scriptEvent.getArguments()) {
				arguments += argument + ", ";
			}
			arguments += "]";
			scriptDebug += arguments.replaceAll(", ]", "]");
		} catch (Exception ignored) {}

        return scriptDebug;
    }
}
