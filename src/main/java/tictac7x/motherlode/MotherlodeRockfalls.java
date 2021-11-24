package tictac7x.motherlode;

import java.util.Set;
import java.util.Arrays;
import com.google.common.collect.ImmutableSet;

public class MotherlodeRockfalls {
    private final Motherlode motherlode;

    private final Set<Rockfall> rockfalls = ImmutableSet.of(
        new Rockfall(3757, 5677, Arrays.asList(Sector.UPSTAIRS_1, Sector.UPSTAIRS_2, Sector.UPSTAIRS_3, Sector.UPSTAIRS_4, Sector.UPSTAIRS_5)),
        new Rockfall(3748, 5684, Arrays.asList(Sector.UPSTAIRS_1, Sector.UPSTAIRS_2, Sector.UPSTAIRS_3, Sector.UPSTAIRS_4, Sector.UPSTAIRS_5))
    );

    public MotherlodeRockfalls(final Motherlode motherlode) {
        this.motherlode = motherlode;
    }

    public Set<Rockfall> getRockfalls() {
        return rockfalls;
    }
}
