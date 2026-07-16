package tictac7x.charges.events;

import net.runelite.api.*;

public class CustomChatMessage {
    public ChatMessageType type;
    public String message;

    public CustomChatMessage(ChatMessageType type, String message) {
        this.type = type;
        this.message = message;
    }

    @Override
    public String toString() {
        return ("MESSAGE | " +
            "type: " + type.name() +
            ", message: " + message
        );
    }
}
