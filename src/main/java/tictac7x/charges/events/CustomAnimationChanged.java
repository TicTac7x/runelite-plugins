package tictac7x.charges.events;

import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.chat.*;

public class CustomAnimationChanged {
    public Actor actor;

    public CustomAnimationChanged(AnimationChanged event) {
        this.actor = event.getActor();
    }

    public void showDebugIds(ChatMessageManager chatMessageManager) {
        chatMessageManager.queue(QueuedMessage.builder()
            .type(ChatMessageType.CONSOLE)
            .runeLiteFormattedMessage("[Item Charges Improved] Animation ID: " + actor.getAnimation())
            .build()
        );
    }
}
