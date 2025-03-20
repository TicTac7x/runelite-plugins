package tictac7x.rooftops.courses;

import tictac7x.rooftops.course.Course;
import tictac7x.rooftops.course.MarkOfGrace;
import tictac7x.rooftops.course.Obstacle;

public class ApeAtoll extends Course {
    public ApeAtoll() {
        super("Ape Atoll",
            // Regions.
            new int[]{ 10794, 11050, 11051 },

            // Obstacles.
            new Obstacle[]{
                new Obstacle(15412, 0, new int[][]{{2754, 2742}}),
                new Obstacle(15414, 0, new int[][]{{2753, 2741}}),
                new Obstacle(15417, 2, new int[][]{{2752, 2741}}),
                new Obstacle(1747, 0, new int[][]{{2746, 2741}}),
                new Obstacle(15487, 0, new int[][]{{2752, 2731}}),
                new Obstacle(16062, 0, new int[][]{{2757, 2734}}),
            },

            new MarkOfGrace[]{
                //
                //
                //
                //
                new MarkOfGrace(2745, 2735, 15487),
            }
        );
    }
}
