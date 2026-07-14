package tictac7x.charges.events;

import net.runelite.api.*;

public class CustomChatMessage {
    public ChatMessageType type;
    public String message;
    private String sender;

    public CustomChatMessage(ChatMessageType type, String message, String sender) {
        this.type = type;
        this.message = message;
        this.sender = sender;
    }

    @Override
    public String toString() {
        return ("MESSAGE | " +
            "type: " + type.name() +
            ", message: " + message +
            ", sender: " + sender
        );
    }
}
