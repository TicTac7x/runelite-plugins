package tictac7x.charges.events;

import net.runelite.api.*;
import net.runelite.client.chat.*;

import java.util.*;

public class CustomGraphicChanged {
    public final String name;
    public final List<Integer> graphicIds;

    public CustomGraphicChanged(String name, List<Integer> graphicIds) {
        this.name = name;
        this.graphicIds = graphicIds;
    }

    public boolean hasGraphicId(int graphicId) {
        return graphicIds.contains(graphicId);
    }

    public void showDebugIds(ChatMessageManager chatMessageManager) {
        for (int graphicId : graphicIds) {
            chatMessageManager.queue(QueuedMessage.builder()
                .type(ChatMessageType.CONSOLE)
                .runeLiteFormattedMessage("[Item Charges Improved] Graphic ID: " + graphicId)
                .build()
            );
        }
    }

    @Override
    public String toString() {
        String string = "GRAPHIC CHANGED | actor: " + name;

        for (int graphicId : graphicIds) {
            string += ", graphic id: " + graphicId;
        }

        return string;
    }
}