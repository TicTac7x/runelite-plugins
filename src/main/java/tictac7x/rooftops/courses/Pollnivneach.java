package tictac7x.rooftops.courses;

import tictac7x.rooftops.course.Course;
import tictac7x.rooftops.course.MarkOfGrace;
import tictac7x.rooftops.course.Obstacle;

public class Pollnivneach extends Course {
    public Pollnivneach() {
        super("Pollnivneach",
            // Regions.
            new int[]{13101,13102,13103,13357,13358,13359,13613,13614,13615},

            // Obstacles.
            new Obstacle[]{
                new Obstacle(14935, 0, new int[][]{{3351, 2962}}),
                new Obstacle(14936, 1, new int[][]{{3349, 2969}, {3350, 2969}, {3351, 2969}}),
                new Obstacle(14937, 1, new int[][]{{3355, 2977}, {3356, 2977}, {3355, 2976}}),
                new Obstacle(14938, 1, new int[][]{{3363, 2977}}),
                new Obstacle(14939, 1, new int[][]{{3367, 2977}, {3368, 2977}, {3369, 2977}}),
                new Obstacle(14940, 1, new int[][]{{3365, 2982}}),
                new Obstacle(14941, 2, new int[][]{{3358, 2985}}),
                new Obstacle(14944, 2, new int[][]{{3359, 2996}, {3360, 2996}, {3361, 2996}}),
                new Obstacle(14945, 2, new int[][]{{3363, 3001}}),
            },

            new MarkOfGrace[]{
                //
                new MarkOfGrace(3346, 2968, 14936),
                new MarkOfGrace(3353, 2975, 14937),
                //
                //
                //
                new MarkOfGrace(3359, 2983, 14941),
                new MarkOfGrace(3362, 2993, 14944),
                new MarkOfGrace(3357, 3002, 14945)
            }
        );
    }
}