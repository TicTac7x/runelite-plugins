package tictac7x.rooftops.courses;

import tictac7x.rooftops.course.Course;
import tictac7x.rooftops.course.MarkOfGrace;
import tictac7x.rooftops.course.Obstacle;

public class Seers extends Course {
    public Seers() {
        super("Seers' Village",
            // Regions.
            new int[]{10549,10550,10551,10805,10806,10807,11061,11062,11063},

            // Obstacles.
            new Obstacle[]{
                new Obstacle(14927, 0, new int[][]{{2729, 3489}}),
                new Obstacle(14928, 3, new int[][]{{2720, 3492}, {2720, 3493}, {2720, 3494}, {2720, 3495}, {2720, 3496}}),
                new Obstacle(14932, 2, new int[][]{{2710, 3489}}),
                new Obstacle(14929, 2, new int[][]{{2710, 3476}, {2711, 3476}, {2712, 3476}, {2713, 3476}, {2714, 3476}}),
                new Obstacle(14930, 3, new int[][]{{2700, 3469}, {2701, 3469}, {2702, 3469}, {2703, 3469}}),
                new Obstacle(14931, 2, new int[][]{{2703, 3461}, {2703, 3462}, {2703, 3463}, {2703, 3464}, {2703, 3465}})
            },

            new MarkOfGrace[]{
                //
                new MarkOfGrace(2727, 3493, 14928),
                new MarkOfGrace(2706, 3493, 14932), new MarkOfGrace(2709, 3493, 14932),
                new MarkOfGrace(2712, 3481, 14929),
                //
                new MarkOfGrace(2698, 3465, 14931)
            }
        );
    }
}