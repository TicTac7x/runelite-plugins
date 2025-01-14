package tictac7x.gotr.store;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GroundObjectSpawned;
import net.runelite.api.events.NpcSpawned;
import tictac7x.gotr.types.BarrierPosition;

import java.util.*;

public class Barriers {
    private Optional<GameObject> airAltar = Optional.empty();
    public Map<BarrierPosition, Barrier> barriers = new LinkedHashMap<>();

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
            } else if (x == 10 && y == -13) {
                barriers.get(BarrierPosition.TWO).updateNpc(event);
            } else if (x == 7 && y == -16) {
                barriers.get(BarrierPosition.THREE).updateNpc(event);
            } else if (x == 3 && y == -17) {
                barriers.get(BarrierPosition.FOUR).updateNpc(event);
            } else if (x == -2 && y == -16) {
                barriers.get(BarrierPosition.FIVE).updateNpc(event);
            } else if (x == -5 && y == -13) {
                barriers.get(BarrierPosition.SIX).updateNpc(event);
            } else if (x == -6 && y == -8) {
                barriers.get(BarrierPosition.SEVEN).updateNpc(event);
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
        return (
            event.getGroundObject().getId() == 43736 ||
            event.getGroundObject().getId() >= 43739 && event.getGroundObject().getId() <= 43743
        );
    }

    private boolean isBarrierNpc(final NpcSpawned event) {
        return event.getNpc().getId() >= 11418 && event.getNpc().getId() <= 11425;
    }
}
