package tictac7x.rooftops.courses;

import tictac7x.rooftops.MarkOfGrace;

public class RooftopCourseVarlamore extends Course {
    public RooftopCourseVarlamore() {
        super("Varlamore",
            // Regions.
            new int[]{6701,6445,},

            // Obstacles.
            new Obstacle[]{
                new Obstacle(55178, 0, new int[][]{{1652, 2931}}),
                new Obstacle(55180, 1, new int[][]{{1655, 2925}})
                    .completeAt(1649, 2910)
                    .nextObstacle(55191, 55184),

                // Basic.
                new Obstacle(55184, 1, new int[][]{{1646, 2910}})
                    .maxLevel(61),
                new Obstacle(55186, 1, new int[][]{{1631, 2910}})
                    .maxLevel(61),
                new Obstacle(55190, 1, new int[][]{{1626, 2932}})
                    .maxLevel(61)
                    .nextObstacle(55179),

                // Advanced.
                new Obstacle(55191, 1, new int[][]{{1648, 2909}})
                    .minLevel(62),
                new Obstacle(55192, 2, new int[][]{{1646, 2907}})
                    .minLevel(62),
                new Obstacle(55194, 2, new int[][]{{1633, 2908}})
                    .minLevel(62)
                    .completeAt(1624, 2931)
                    .nextObstacle(55179),

                // End.
                new Obstacle(55179, 2, new int[][]{{1626, 2933}}),
            },

            new MarkOfGrace[]{}
        );
    }
}

