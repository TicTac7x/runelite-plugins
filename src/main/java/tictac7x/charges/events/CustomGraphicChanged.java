package tictac7x.charges.events;

import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.chat.*;

public class CustomGraphicChanged {
    private Actor actor;

    public CustomGraphicChanged(GraphicChanged event) {
        this.actor = event.getActor();
    }

    public boolean isLocalPlayer(Client client) {
        return actor == client.getLocalPlayer();
    }

    public boolean hasGraphicId(int graphicId) {
        return actor.hasSpotAnim(graphicId);
    }

    public void showDebugIds(ChatMessageManager chatMessageManager) {
        for (ActorSpotAnim graphic : actor.getSpotAnims()) {
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

        for (ActorSpotAnim graphic : actor.getSpotAnims()) {
            string += ", graphic id: " + graphic.getId();
        }

        return string;
    }
}
