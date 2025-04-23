package tictac7x.charges.customEvents;

import net.runelite.api.ChatMessageType;
import net.runelite.api.events.ChatMessage;

public class CustomChatMessage {
    public final ChatMessageType type;
    public final String message;
    private final String sender;

    public CustomChatMessage(final ChatMessage event) {
        this.type = event.getType();
        this.message = event.getMessage().replaceAll("</?col.*?>", "").replaceAll("<br>", " ").replaceAll("\u00A0"," ");
        this.sender = event.getSender();
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
