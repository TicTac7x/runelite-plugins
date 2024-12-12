package tictac7x.rooftops.courses;

import tictac7x.rooftops.MarkOfGrace;

public class RooftopCourseAlKharid extends Course {
    public RooftopCourseAlKharid() {
        super("Al Kharid",
            // Regions.
            new int[]{ 12848, 12849,12850,13104,13105,13106,13360,13361,13362 },

            // Obstacles.
            new Obstacle[]{
                new Obstacle(11633, 0, new int[][]{{3273, 3195}}),
                new Obstacle(14398, 3, new int[][]{{3272, 3181}}),
                new Obstacle(14402, 3, new int[][]{{3265, 3166}, {3266, 3166}, {3267, 3166}, {3268, 3166}, {3269, 3166}}),
                new Obstacle(14403, 3, new int[][]{{3302, 3163}, {3303, 3163}}),
                new Obstacle(14404, 1, new int[][]{{3318, 3166}}),
                new Obstacle(11634, 2, new int[][]{{3316, 3179}}),
                new Obstacle(14409, 3, new int[][]{{3313, 3186}}),
                new Obstacle(14399, 3, new int[][]{{3300, 3193}}),
            },

            new MarkOfGrace[]{
                //
                new MarkOfGrace(3277, 3186, 14398),
                new MarkOfGrace(3266, 3163, 14402),
                //
                new MarkOfGrace(3315, 3161, 14404)
                //
                //
                //
            }
        );
    }
}
