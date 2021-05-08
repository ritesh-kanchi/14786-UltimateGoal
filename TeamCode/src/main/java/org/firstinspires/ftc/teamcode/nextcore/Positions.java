package org.firstinspires.ftc.teamcode.nextcore;

public class Positions {

    // FIELD SPECIFIC DATA
    public double MAX_VALUE = 72;
    public double MIN_VALUE = -MAX_VALUE;

    public double ONE_TILE = 24;
    public double HALF_TILE = ONE_TILE/2;

    public double ORIGIN = 0;

    public double LAUNCH_LINE_X = 4;

    public double TAPE_WIDTH = 2;

    // ROBOT SPECIFIC DATA
    public static double ROBOT_WIDTH = 18; // LENGTH OF INTAKE SIDE
    public static double ROBOT_LENGTH = 18; // LENGTH OF WHEELS SIDE

    public Positions() {}

    // RED SPECIFIC DATA

    public double RED_STARTING_X = againstWallValueX(MIN_VALUE);  // STARTING X FOR AUTON
    public static double RED_STARTING_Y = -35;  // STARTING Y FOR AUTON

    public static double RED_SHOOTING_X = 0;
    public static double RED_SHOOTING_Y = 0;

    public static double RED_ENDING_X = 10; // STARTING X FOR TELEOP + ENDING X FOR AUTON
    public static double RED_ENDING_Y = -35; // STARTING Y FOR TELEOP + ENDING Y FOR AUTON

    public static double RED_WOBBLE_X_0 = 0;
    public static double RED_WOBBLE_Y_0 = 0;

    public static double RED_WOBBLE_X_1 = 0;
    public static double RED_WOBBLE_Y_1 = 0;

    public static double RED_WOBBLE_X_4 = 0;
    public static double RED_WOBBLE_Y_4 = 0;

    // from left to right

    public static double RED_POWERSHOT_X_1 = 0;
    public static double RED_POWERSHOT_Y_1 = 0;

    public static double RED_POWERSHOT_X_2 = 0;
    public static double RED_POWERSHOT_Y_2 = 0;

    public static double RED_POWERSHOT_X_3 = 0;
    public static double RED_POWERSHOT_Y_3 = 0;

    // BLUE SPECIFIC DATA


    public double BLUE_STARTING_X = againstWallValueX(MIN_VALUE);  // STARTING X FOR AUTON
    public static double BLUE_STARTING_Y = -35;  // STARTING Y FOR AUTON

    public static double BLUE_SHOOTING_X = 0;
    public static double BLUE_SHOOTING_Y = 0;

    public static double BLUE_ENDING_X = 10; // STARTING X FOR TELEOP + ENDING X FOR AUTON
    public static double BLUE_ENDING_Y = -35; // STARTING Y FOR TELEOP + ENDING Y FOR AUTON

    public static double BLUE_WOBBLE_X_0 = 0;
    public static double BLUE_WOBBLE_Y_0 = 0;

    public static double BLUE_WOBBLE_X_1 = 0;
    public static double BLUE_WOBBLE_Y_1 = 0;

    public static double BLUE_WOBBLE_X_4 = 0;
    public static double BLUE_WOBBLE_Y_4 = 0;

    // from left to right

    public static double BLUE_POWERSHOT_X_1 = 0;
    public static double BLUE_POWERSHOT_Y_1 = 0;

    public static double BLUE_POWERSHOT_X_2 = 0;
    public static double BLUE_POWERSHOT_Y_2 = 0;

    public static double BLUE_POWERSHOT_X_3 = 0;
    public static double BLUE_POWERSHOT_Y_3 = 0;


    // GETS X VALUE AGAINST THE WALL
    public double againstWallValueX(double value) {
         if(value < 0) {
             return value + ROBOT_LENGTH /2;
         } else {
             return value - ROBOT_LENGTH/2;
         }
    }
}
