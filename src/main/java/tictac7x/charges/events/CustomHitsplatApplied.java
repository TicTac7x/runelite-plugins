package tictac7x.charges.events;

import net.runelite.api.*;
import net.runelite.api.events.*;

public class CustomHitsplatApplied {
    public Actor actor;
    public int type;
    public int amount;
    public boolean toMe;
    public boolean byMe;
    public boolean byOthers;

    public CustomHitsplatApplied(HitsplatApplied event, Client client) {
        this.actor = event.getActor();
        this.type = event.getHitsplat().getHitsplatType();
        this.amount = event.getHitsplat().getAmount();
        this.toMe = event.getActor() == client.getLocalPlayer();
        this.byMe = event.getHitsplat().isMine();
        this.byOthers = event.getHitsplat().isOthers();
    }

    @Override
    public String toString() {
        return ("HITSPLAT | " +
			"actor: " + actor.getName() +
			", type: " + type +
			", amount:" + amount +
			", by others: " + byOthers +
			", by me: " + byMe
		);
    }
}
