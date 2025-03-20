package tictac7x.rooftops.courses;

import tictac7x.rooftops.course.Course;
import tictac7x.rooftops.course.MarkOfGrace;
import tictac7x.rooftops.course.Obstacle;

public class Ardougne extends Course {
    public Ardougne() {
        super("Ardougne",
            // Regions.
            new int[]{10290,10291,10292,10546,10547,10548,10802,10803,10804},

            // Obstacles.
            new Obstacle[]{
                new Obstacle(15608, 0, new int[][]{{2673, 3298}}),
                new Obstacle(15609, 3, new int[][]{{2670, 3310}, {2671, 3310}}),
                new Obstacle(26635, 3, new int[][]{{2661, 3318}}),
                new Obstacle(15610, 3, new int[][]{{2653, 3317}, {2653, 3318}}),
                new Obstacle(15611, 3, new int[][]{{2653, 3308}, {2653, 3309}}),
                new Obstacle(28912, 3, new int[][]{{2654, 3300}}),
                new Obstacle(15612, 3, new int[][]{{2656, 3296}, {2657, 3296}})
            },

            new MarkOfGrace[]{
                new MarkOfGrace(2657, 3318, 15610)
            }
        );
    }
}

