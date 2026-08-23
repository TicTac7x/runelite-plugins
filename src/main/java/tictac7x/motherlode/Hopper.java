package tictac7x.motherlode;

import net.runelite.api.Client;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.VarbitChanged;
import tictac7x.motherlode.ids.AnimationId;
import tictac7x.motherlode.ids.VarbitId;

public class Hopper {
    private final Client client;
    private final Inventory inventory;
    private int paydirt = 0;
    private int lastSackPaydirt = -1;

    public Hopper(final Client client, final Inventory inventory) {
        this.client = client;
        this.inventory = inventory;
    }

    public int getPaydirt() {
        return paydirt;
    }

    public void onAnimationChanged(final AnimationChanged event) {
        if (event.getActor() == client.getLocalPlayer() && event.getActor().getAnimation() == AnimationId.DEPOSIT_PAYDIRT) {
            paydirt += inventory.getPaydirt();
        }
    }

    public void onVarbitChanged(final VarbitChanged event) {
        if (event.getVarbitId() != VarbitId.MOTHERLODE_SACK_PAYDIRT) return;

        final int sackPaydirt = event.getValue();
        if (lastSackPaydirt >= 0 && sackPaydirt > lastSackPaydirt) {
            paydirt = Math.max(0, paydirt - (sackPaydirt - lastSackPaydirt));
        }
        lastSackPaydirt = sackPaydirt;
    }
}
