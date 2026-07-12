package tictac7x.motherlode;

import net.runelite.api.Client;
import tictac7x.motherlode.sectors.Sector;
import tictac7x.motherlode.sectors.Sectors;

import java.util.Arrays;
import java.util.List;

import static tictac7x.motherlode.sectors.Sector.DOWNSTAIRS;

public class Character {
    private final Client client;

    public Character(final Client client) {
        this.client = client;
    }

    private final int[] MOTHERLODE_REGIONS = new int[]{ 14679, 14680, 14681, 14935, 14936, 14937, 15191, 15192, 15193 };

    private boolean inMotherlode = false;
    private List<Sector> sectors = Arrays.asList(DOWNSTAIRS);

    public boolean isInMotherlode() {
        return inMotherlode;
    }

    public List<Sector> getSectors() {
        return sectors;
    }

    public void onGameTick() {
        this.sectors = Sectors.getSectors(client.getLocalPlayer().getWorldLocation().getX(), client.getLocalPlayer().getWorldLocation().getY(), true);
    }

    public void checkIsInMotherlode() {
        for (final int playerRegion : client.getLocalPlayer().getWorldView().getMapRegions()) {
            for (final int motherlodeRegion : MOTHERLODE_REGIONS) {
                if (motherlodeRegion == playerRegion) {
                    inMotherlode = true;
                    return;
                }
            }
        }

        inMotherlode = false;
    }
}
