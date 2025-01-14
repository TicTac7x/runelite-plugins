package tictac7x.gotr.store;

import net.runelite.api.events.GroundObjectSpawned;
import net.runelite.api.events.NpcSpawned;
import tictac7x.gotr.types.BarrierPosition;

import java.util.*;

public class Barriers {
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

    public void onNpcSpawned(final NpcSpawned event) {
        if (isBarrierNpc(event)) {
            final int x = event.getNpc().getLocalLocation().getX();
            final int y = event.getNpc().getLocalLocation().getY();

            if (x == 5952 && y == 10176) {
                barriers.get(BarrierPosition.ONE).updateNpc(event);
            } else if (x == 6144 && y == 10752) {
                barriers.get(BarrierPosition.TWO).updateNpc(event);
            } else if (x == 6528 && y == 11136) {
                barriers.get(BarrierPosition.THREE).updateNpc(event);
            } else if (x == 7104 && y == 11328) {
                barriers.get(BarrierPosition.FOUR).updateNpc(event);
            } else if (x == 7680 && y == 11136) {
                barriers.get(BarrierPosition.FIVE).updateNpc(event);
            } else if (x == 8064 && y == 10752) {
                barriers.get(BarrierPosition.SIX).updateNpc(event);
            } else if (x == 8256 && y == 10176) {
                barriers.get(BarrierPosition.SEVEN).updateNpc(event);
            }
        }
    }

    public void groundObjectSpawned(final GroundObjectSpawned event) {
        if (isBarrierGroundObject(event)) {
            final int x = event.getGroundObject().getLocalLocation().getX();
            final int y = event.getGroundObject().getLocalLocation().getY();

            if (x == 6208 && y == 10176) {
                barriers.get(BarrierPosition.ONE).updateGroundObject(event);
            } else if (x == 6336 && y == 10688) {
                barriers.get(BarrierPosition.TWO).updateGroundObject(event);
            } else if (x == 6592 && y == 10944) {
                barriers.get(BarrierPosition.THREE).updateGroundObject(event);
            } else if (x == 7104 && y == 11072) {
                barriers.get(BarrierPosition.FOUR).updateGroundObject(event);
            } else if (x == 7616 && y == 10944) {
                barriers.get(BarrierPosition.FIVE).updateGroundObject(event);
            } else if (x == 7872 && y == 10688) {
                barriers.get(BarrierPosition.SIX).updateGroundObject(event);
            } else if (x == 8000 && y == 10176) {
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
