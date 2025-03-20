package tictac7x.rooftops.courses;

import tictac7x.rooftops.course.Course;
import tictac7x.rooftops.course.MarkOfGrace;
import tictac7x.rooftops.course.Obstacle;

public class Varrock extends Course {
    public Varrock() {
        super("Varrock",
            // Regions.
            new int[]{12596,12597,12598,12852,12853,12854},

            // Obstacles.
            new Obstacle[]{
                new Obstacle(14412, 0, new int[][]{{3221, 3414}}),
                new Obstacle(14413, 3, new int[][]{{3213, 3414}}),
                new Obstacle(14414, 3, new int[][]{{3200, 3416}, {3200, 3417}}),
                new Obstacle(14832, 1, new int[][]{{3192, 3416}, {3193, 3416}, {3194, 3416}}),
                new Obstacle(14833, 3, new int[][]{{3193, 3401}, {3194, 3401}, {3195, 3401}, {3196, 3401}, {3197, 3401}, {3198, 3401}}),
                new Obstacle(14834, 3, new int[][]{{3209, 3397}, {3209, 3398}, {3209, 3399}, {3209, 3400}, {3209, 3401}}),
                new Obstacle(14835, 3, new int[][]{{3233, 3402}}),
                new Obstacle(14836, 3, new int[][]{{3236, 3409}, {3237, 3409}, {3238, 3409}, {3239, 3409}, {3240, 3409}}),
                new Obstacle(14841, 3, new int[][]{{3236, 3416}, {3237, 3416}, {3238, 3416}, {3239, 3416}, {3240, 3416}}),
            },

            new MarkOfGrace[]{
                //
                //
                //
                new MarkOfGrace(3195, 3416, 14832),
                //
                new MarkOfGrace(3186, 3395, 14834), new MarkOfGrace(3191, 3394, 14834),
                new MarkOfGrace(3220, 3402, 14835),
                new MarkOfGrace(3238, 3408, 14836)
                //
            }
        );
    }
}
