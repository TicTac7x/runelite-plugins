package tictac7x.gotr.store;

import net.runelite.api.GroundObject;
import net.runelite.api.NPC;
import net.runelite.api.events.GroundObjectSpawned;
import net.runelite.api.events.NpcSpawned;
import tictac7x.gotr.types.BarrierLevel;

import java.util.Optional;

public class Barrier {
    private Optional<GroundObject> groundObject = Optional.empty();
    private Optional<NPC> npc = Optional.empty();
    private int health = 0;

    public BarrierLevel getLevel() {
        if (groundObject.isPresent()) {
            switch (groundObject.get().getId()) {
                case 43736:
                    return BarrierLevel.BROKEN;
                case 43739:
                    return BarrierLevel.NOT_BUILT;
                case 43740:
                    return BarrierLevel.ONE;
                case 43741:
                    return BarrierLevel.TWO;
                case 43742:
                    return BarrierLevel.THREE;
                case 43743:
                    return BarrierLevel.FOUR;
            }
        }

        return BarrierLevel.NOT_BUILT;
    }

    public void updateGroundObject(final GroundObjectSpawned event) {
        this.groundObject = Optional.of(event.getGroundObject());
    }

    public void updateNpc(final NpcSpawned event) {
        this.npc = Optional.of(event.getNpc());
    }

    public int getHealth() {
        final BarrierLevel level = getLevel();

        if (
            level == BarrierLevel.BROKEN ||
            level == BarrierLevel.NOT_BUILT
        ) {
            health = 0;
        } else if (npc.isPresent() && npc.get().getHealthRatio() != -1) {
            health = npc.get().getHealthRatio() * 2;
        }

        return health;
    }
}
