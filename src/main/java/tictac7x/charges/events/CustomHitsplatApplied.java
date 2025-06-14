package tictac7x.charges.events;

import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.HitsplatID;
import net.runelite.api.annotations.HitsplatType;
import net.runelite.api.events.HitsplatApplied;

public class CustomHitsplatApplied {
    public final Actor actor;
    public final int type;
    public final int amount;
    public final boolean toMe;
    public final boolean byMe;
    public final boolean byOthers;

    public CustomHitsplatApplied(final HitsplatApplied event, final Client client) {
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
