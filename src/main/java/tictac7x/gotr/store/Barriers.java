package tictac7x.gotr.store;

import net.runelite.api.GameObject;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GroundObjectSpawned;
import net.runelite.api.events.NpcSpawned;
import tictac7x.gotr.types.BarrierPosition;

import java.util.*;

public class Barriers {
    public Map<BarrierPosition, Barrier> barriers = new LinkedHashMap<>();

    private Optional<GameObject> airAltar = Optional.empty();
    public final Set<BarrierPosition> barriersBuiltDuringGame = new HashSet<>();

    public Barriers() {
        barriers.put(BarrierPosition.ONE, new Barrier());
        barriers.put(BarrierPosition.TWO, new Barrier());
        barriers.put(BarrierPosition.THREE, new Barrier());
        barriers.put(BarrierPosition.FOUR, new Barrier());
        barriers.put(BarrierPosition.FIVE, new Barrier());
        barriers.put(BarrierPosition.SIX, new Barrier());
        barriers.put(BarrierPosition.SEVEN, new Barrier());
    }

    public void onGameObjectSpawned(final GameObjectSpawned event) {
        if (event.getGameObject().getId() == 43701) {
            airAltar = Optional.of(event.getGameObject());
        }
    }

    public void onNpcSpawned(final NpcSpawned event) {
        if (isBarrierNpc(event) && airAltar.isPresent()) {
            final int x = airAltar.get().getWorldLocation().getX() - event.getNpc().getWorldLocation().getX();
            final int y = airAltar.get().getWorldLocation().getY() - event.getNpc().getWorldLocation().getY();

            if (x == 12 && y == -8) {
                barriers.get(BarrierPosition.ONE).updateNpc(event);
                barriersBuiltDuringGame.add(BarrierPosition.ONE);
            } else if (x == 10 && y == -13) {
                barriers.get(BarrierPosition.TWO).updateNpc(event);
                barriersBuiltDuringGame.add(BarrierPosition.TWO);
            } else if (x == 7 && y == -16) {
                barriers.get(BarrierPosition.THREE).updateNpc(event);
                barriersBuiltDuringGame.add(BarrierPosition.THREE);
            } else if (x == 3 && y == -17) {
                barriers.get(BarrierPosition.FOUR).updateNpc(event);
                barriersBuiltDuringGame.add(BarrierPosition.FOUR);
            } else if (x == -2 && y == -16) {
                barriers.get(BarrierPosition.FIVE).updateNpc(event);
                barriersBuiltDuringGame.add(BarrierPosition.FIVE);
            } else if (x == -5 && y == -13) {
                barriers.get(BarrierPosition.SIX).updateNpc(event);
                barriersBuiltDuringGame.add(BarrierPosition.SIX);
            } else if (x == -6 && y == -8) {
                barriers.get(BarrierPosition.SEVEN).updateNpc(event);
                barriersBuiltDuringGame.add(BarrierPosition.SEVEN);
            }
        }
    }

    public void groundObjectSpawned(final GroundObjectSpawned event) {
        if (isBarrierGroundObject(event) && airAltar.isPresent()) {
            final int x = airAltar.get().getWorldLocation().getX() - event.getGroundObject().getWorldLocation().getX();
            final int y = airAltar.get().getWorldLocation().getY() - event.getGroundObject().getWorldLocation().getY();

            if (x == 9 && y == -9) {
                barriers.get(BarrierPosition.ONE).updateGroundObject(event);
            } else if (x == 8 && y == -13) {
                barriers.get(BarrierPosition.TWO).updateGroundObject(event);
            } else if (x == 6 && y == -15) {
                barriers.get(BarrierPosition.THREE).updateGroundObject(event);
            } else if (x == 2 && y == -16) {
                barriers.get(BarrierPosition.FOUR).updateGroundObject(event);
            } else if (x == -2 && y == -15) {
                barriers.get(BarrierPosition.FIVE).updateGroundObject(event);
            } else if (x == -4 && y == -13) {
                barriers.get(BarrierPosition.SIX).updateGroundObject(event);
            } else if (x == -5 && y == -9) {
                barriers.get(BarrierPosition.SEVEN).updateGroundObject(event);
            }
        }
    }

    private boolean isBarrierGroundObject(final GroundObjectSpawned event) {
        final int groundObjectId = event.getGroundObject().getId();

        return (
            groundObjectId == 43736 || // broken
            groundObjectId == 43739 || // not built
            groundObjectId == 43740 || // level 1
            groundObjectId == 43741 || // levle 2
            groundObjectId == 43742 || // level 3
            groundObjectId == 43743    // level 4
        );
    }

    private boolean isBarrierNpc(final NpcSpawned event) {
        return event.getNpc().getId() >= 11418 && event.getNpc().getId() <= 11425;
    }

    public void onGameEnd() {
        barriersBuiltDuringGame.clear();
    }
}
