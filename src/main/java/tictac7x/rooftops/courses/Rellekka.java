package tictac7x.rooftops.courses;

import tictac7x.rooftops.course.Course;
import tictac7x.rooftops.course.MarkOfGrace;
import tictac7x.rooftops.course.Obstacle;

public class Rellekka extends Course {
    public Rellekka() {
        super("Rellekka",
            // Regions.
            new int[]{10296,10297,10552,10553,10808,10809},

            // Obstacles.
            new Obstacle[]{
                new Obstacle(14946, 0, new int[][]{{2625, 3677}}),
                new Obstacle(14947, 3, new int[][]{{2623, 3671}, {2622, 3671}, {2621, 3671}}),
                new Obstacle(14987, 3, new int[][]{{2623, 3658}}),
                new Obstacle(14990, 3, new int[][]{{2629, 3656}, {2630, 3656}}),
                new Obstacle(14991, 3, new int[][]{{2643, 3654}, {2644, 3654}}),
                new Obstacle(14992, 3, new int[][]{{2647, 3663}}),
                new Obstacle(14994, 3, new int[][]{{2654, 3676}}),
            },

            new MarkOfGrace[]{
                //
                new MarkOfGrace(2623, 3675, 14947),
                new MarkOfGrace(2627, 3654, 14990), new MarkOfGrace(2629, 3653, 14990), new MarkOfGrace(2628, 3652, 14990),
                //
                new MarkOfGrace(2641, 3650, 14991), new MarkOfGrace(2642, 3651, 14991)
            }
        );
    }
}