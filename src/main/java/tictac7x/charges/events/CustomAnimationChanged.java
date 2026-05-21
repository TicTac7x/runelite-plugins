package tictac7x.charges.events;

import net.runelite.api.Actor;
import net.runelite.api.ChatMessageType;
import net.runelite.api.events.AnimationChanged;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;

public class CustomAnimationChanged {
    public final Actor actor;

    public CustomAnimationChanged(final AnimationChanged event) {
        this.actor = event.getActor();
    }

    public void showDebugIds(final ChatMessageManager chatMessageManager) {
        chatMessageManager.queue(QueuedMessage.builder()
            .type(ChatMessageType.CONSOLE)
            .runeLiteFormattedMessage("[Item Charges Improved] Animation ID: " + actor.getAnimation())
            .build()
        );
    }
}
