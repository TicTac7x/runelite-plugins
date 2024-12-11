package tictac7x.rooftops.courses;

import tictac7x.rooftops.MarkOfGrace;

public class RooftopCourseApeAtoll extends Course {
    public RooftopCourseApeAtoll() {
        super("Ape Atoll",
            // Regions.
            new int[]{ 11050 },

            // Obstacles.
            new Obstacle[]{
                new Obstacle(15412, 0, new int[][]{{2754, 2742}}),
                new Obstacle(15414, 0, new int[][]{{2753, 2741}}),
                new Obstacle(15417, 2, new int[][]{{2752, 2741}}),
                new Obstacle(1747, 0, new int[][]{{2746, 2741}}),
                new Obstacle(15487, 0, new int[][]{{2752, 2731}}),
                new Obstacle(16062, 0, new int[][]{{2757, 2734}}),
            },

            new MarkOfGrace[]{}
        );
    }
}
