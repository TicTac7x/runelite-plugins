package tictac7x.rooftops.courses;

import tictac7x.rooftops.course.Course;
import tictac7x.rooftops.course.MarkOfGrace;
import tictac7x.rooftops.course.Obstacle;

public class Canifis extends Course {
    public Canifis() {
        super("Canifis",
            // Regions.
            new int[]{13621,13622,13623,13877,13878,13879,14133,14134,14135},

            // Obstacles.
            new Obstacle[]{
                new Obstacle(14843, 0, new int[][]{{3508, 3489}}),
                new Obstacle(14844, 2, new int[][]{{3505, 3498}, {3506, 3498}}),
                new Obstacle(14845, 2, new int[][]{{3496, 3503}, {3496, 3504}, {3496, 3505}, {3497, 3504}, {3497, 3505}}),
                new Obstacle(14848, 2, new int[][]{{3486, 3499}}),
                new Obstacle(14846, 3, new int[][]{{3478, 3492}}),
                new Obstacle(14894, 2, new int[][]{{3480, 3483}}),
                new Obstacle(14847, 3, new int[][]{{3503, 3476}, {3504, 3475}, {3504, 3476}}),
                new Obstacle(14897, 2, new int[][]{{3510, 3483}}),
            },

            new MarkOfGrace[]{
                new MarkOfGrace(3506, 3495, 14844),
                new MarkOfGrace(3501, 3505, 14845),
                new MarkOfGrace(3490, 3501, 14848),
                new MarkOfGrace(3478, 3496, 14846),
                new MarkOfGrace(3478, 3484, 14894)
            }
        );
    }
}
