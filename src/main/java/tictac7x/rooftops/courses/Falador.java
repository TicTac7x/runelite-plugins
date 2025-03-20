package tictac7x.rooftops.courses;

import tictac7x.rooftops.course.Course;
import tictac7x.rooftops.course.MarkOfGrace;
import tictac7x.rooftops.course.Obstacle;

public class Falador extends Course {
    public Falador() {
        super("Falador",
            // Regions.
            new int[]{11827,11828,11829,12083,12084,12085},

            // Obstacles.
            new Obstacle[]{
                new Obstacle(14898, 0, new int[][]{{3036, 3341}}),
                new Obstacle(14899, 3, new int[][]{{3040, 3343}}),
                new Obstacle(14901, 3, new int[][]{{3050, 3350}}),
                new Obstacle(14903, 3, new int[][]{{3048, 3359}}),
                new Obstacle(14904, 3, new int[][]{{3044, 3361}, {3044, 3362}, {3044, 3363}, {3044, 3364}}),
                new Obstacle(14905, 3, new int[][]{{3034, 3361}, {3034, 3362}}),
                new Obstacle(14911, 3, new int[][]{{3026, 3353}}),
                new Obstacle(14919, 3, new int[][]{{3018, 3352}, {3017, 3352}, {3016, 3352}}),
                new Obstacle(14920, 3, new int[][]{{3015, 3345}, {3015, 3346}}),
                new Obstacle(14921, 3, new int[][]{{3013, 3343}, {3012, 3343}, {3011, 3343}})
                    .nextObstacle(14922, 14923),
                new Obstacle(14922, 3, new int[][]{{3012, 3334}, {3013, 3334}})
                    .nextObstacle(14924),
                new Obstacle(14923, 3, new int[][]{{3014, 3335}})
                    .nextObstacle(14924),
                new Obstacle(14924, 3, new int[][]{{3018, 3334}, {3018, 3333}, {3018, 3332}}),
                new Obstacle(14925, 3, new int[][]{{3025, 3335}, {3025, 3334}, {3025, 3333}, {3025, 3332}}),
            },

            new MarkOfGrace[]{}
        );
    }
}
