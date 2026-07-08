package tictac7x.charges.events;

import net.runelite.api.Actor;
import net.runelite.api.ActorSpotAnim;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.events.GraphicChanged;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;

public class CustomGraphicChanged {
    private final Actor actor;

    public CustomGraphicChanged(final GraphicChanged event) {
        this.actor = event.getActor();
    }

    public boolean isLocalPlayer(final Client client) {
        return actor == client.getLocalPlayer();
    }

    public boolean hasGraphicId(final int graphicId) {
        return actor.hasSpotAnim(graphicId);
    }

    public void showDebugIds(final ChatMessageManager chatMessageManager) {
        for (final ActorSpotAnim graphic : actor.getSpotAnims()) {
            chatMessageManager.queue(QueuedMessage.builder()
                .type(ChatMessageType.CONSOLE)
                .runeLiteFormattedMessage("[Item Charges Improved] Graphic ID: " + graphic.getId())
                .build()
            );
        }
    }

    @Override
    public String toString() {
        String string = "GRAPHIC CHANGED | actor: " + actor.getName();

        for (final ActorSpotAnim graphic : actor.getSpotAnims()) {
            string += ", graphic id: " + graphic.getId();
        }

        return string;
    }
}
