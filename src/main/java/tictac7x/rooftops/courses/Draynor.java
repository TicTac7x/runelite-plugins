package tictac7x.rooftops.courses;

import tictac7x.rooftops.course.Course;
import tictac7x.rooftops.course.MarkOfGrace;
import tictac7x.rooftops.course.Obstacle;

public class Draynor extends Course {
    public Draynor() {
        super("Draynor Village",
            // Regions.
            new int[]{12082,12338,12339,12594,12595},

            // Obstacles.
            new Obstacle[]{
                new Obstacle(11404, 0, new int[][]{{3103, 3279}}),
                new Obstacle(11405, 3, new int[][]{{3098, 3277}}),
                new Obstacle(11406, 3, new int[][]{{3092, 3276}}),
                new Obstacle(11430, 3, new int[][]{{3089, 3264}}),
                new Obstacle(11630, 3, new int[][]{{3088, 3256}}),
                new Obstacle(11631, 3, new int[][]{{3095, 3255}}),
                new Obstacle(11632, 3, new int[][]{{3102, 3261}})
            },

            new MarkOfGrace[]{
                //
                new MarkOfGrace(3098, 3281, 11405),
                new MarkOfGrace(3089, 3274, 11406),
                new MarkOfGrace(3094, 3266, 11430),
                //
                //
                new MarkOfGrace(3100, 3257, 11632), new MarkOfGrace(3097, 3259, 11632)
            }
        );
    }
}
