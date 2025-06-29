package tictac7x.rooftops.courses;

import tictac7x.rooftops.course.Course;
import tictac7x.rooftops.course.Portal;
import tictac7x.rooftops.course.Obstacle;

public class Prifddinas extends Course {
    public Prifddinas() {
        super("Prifddinas",
            // Regions.
            new int[]{12895,13151,9012,9013},

            // Obstacles.
            new Obstacle[]{
                new Obstacle(36221, 0, new int[][]{{3254, 6109}}), //Ladder (start of course)
                new Obstacle(36225, 2, new int[][]{{3257, 6105}}), //Tightrope
                new Obstacle(36227, 2, new int[][]{{3273, 6107}}), //Chimney
                new Obstacle(36228, 2, new int[][]{{3269, 6116}}),
                new Obstacle(36229, 0, new int[][]{{3269, 6118}}), //Dark Hole
                new Obstacle(36231, 0, new int[][]{{2270, 3393}}).completeAt(2269, 3393).nextObstacle(36233), //Ladder (doesn't give exp)
                new Obstacle(36233, 2, new int[][]{{2264, 3390}}), //Rope Bridge
                new Obstacle(36234, 2, new int[][]{{2254, 3390}, {2253, 3390}}), //Tightrope
                new Obstacle(36235, 2, new int[][]{{2246, 3399}}), //Rope Bridge
                new Obstacle(36236, 2, new int[][]{{2244, 3409}, {2243, 3409}}), //Tightrope
                new Obstacle(36237, 2, new int[][]{{2253, 3418}, {2254,3418}}), //Rope Bridge
                new Obstacle(36238, 0, new int[][]{{2258, 3432}}), //Dark Hole

                //new Obstacle(36232, 0, new int[][]{{3267, 6145}}), //Ladder when failing Tightrope, not sure how to implement this? Also very hard to test...
            },

            /*
            new Portal[]{ // Portals
                new Portal(3257, 6111, 36241), // Portal on top of bank
                new Portal(3270, 6116, 36247), // Portal by Dark Hole in garden
                new Portal(2258, 3386, 36243), // Portal after first Rope Bridge
                new Portal(2243, 3395, 36244), // Portal before second Rope Bridge
                new Portal(2249, 3419, 36246), // Portal before final Tightrope
            }
            */

           new Portal[]{ 
                new Portal(36241, 2, new int[][]{{3257, 6111}}).nextObstacle(36225), // Portal on top of bank
                new Portal(36242, 0, new int[][]{{3270, 6116}}).nextObstacle(36231), // Portal by Dark Hole in garden (or 36247?)
                new Portal(36243, 2, new int[][]{{2258, 3386}}).nextObstacle(36234), // Portal after first Rope Bridge
                new Portal(36244, 2, new int[][]{{2243, 3395}}).nextObstacle(36235), // Portal before second Rope Bridge
                new Portal(36245, 2, new int[][]{{2248, 3405}}).nextObstacle(36236), // Portal before Tightrope
                new Portal(36246, 2, new int[][]{{2249, 3419}}).nextObstacle(36237), // Portal before final Tightrope
           }
        );
    }
}