package org.firstinspires.ftc.teamcode.nextcore;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Positions {

    // FIELD SPECIFIC DATA
    public static double MAX_VALUE = 72;
    public double MIN_VALUE = -MAX_VALUE;

    public static double TILE = 24;
    public static double HALF_TILE = TILE / 2;
    public static double ONE_HALF_TILE = TILE + HALF_TILE;

    public static double ORIGIN = 0;

    public static double ANGLE_SCALE = 16/15;

    public static double TAPE_WIDTH = 2;

    // launch line ~ 80 from audience perimeter wall, tape width is 2, want to be on the middle
    public static double LAUNCH_LINE_X = 80-MAX_VALUE + (TAPE_WIDTH/2);

    // ROBOT SPECIFIC DATA
    public static double ROBOT_LENGTH = 18; // LENGTH OF WHEELS SIDE
    public static double ROBOT_WIDTH = ROBOT_LENGTH; // LENGTH OF INTAKE SIDE

    public Positions() {
    }

    // RED SPECIFIC DATA

    public double RED_STARTING_X = againstWallValueX(MIN_VALUE);  // STARTING X FOR AUTON
    public static double RED_STARTING_Y = -25.5;  // STARTING Y FOR AUTON

    public static double RED_SHOOTING_X = -1;
    public static double RED_SHOOTING_Y = -39;

    public static double RED_ENDING_X = LAUNCH_LINE_X; // STARTING X FOR TELEOP + ENDING X FOR AUTON
    public static double RED_ENDING_Y = RED_SHOOTING_Y; // STARTING Y FOR TELEOP + ENDING Y FOR AUTON

    public static double RED_WOBBLE_X_0 = HALF_TILE;
    public static double RED_WOBBLE_Y_0 = -ONE_HALF_TILE;

    public static double RED_WOBBLE_X_1 = ONE_HALF_TILE;
    public static double RED_WOBBLE_Y_1 = -HALF_TILE;

    public static double RED_WOBBLE_X_4 = 2*TILE+HALF_TILE;
    public static double RED_WOBBLE_Y_4 = -ONE_HALF_TILE;

    // from left to right

    public static double RED_POWERSHOT_X = RED_SHOOTING_X;

    public static double RED_POWERSHOT_Y_1 = -31;
    public static double RED_POWERSHOT_Y_2 = -37;
    public static double RED_POWERSHOT_Y_3 = -42;

    // BLUE SPECIFIC DATA

    public double BLUE_STARTING_X = againstWallValueX(MIN_VALUE);  // STARTING X FOR AUTON
    public static double BLUE_STARTING_Y = -49.5;  // STARTING Y FOR AUTON

    public static double BLUE_SHOOTING_X = -1;
    public static double BLUE_SHOOTING_Y = 39;

    public static double BLUE_ENDING_X = LAUNCH_LINE_X; // STARTING X FOR TELEOP + ENDING X FOR AUTON
    public static double BLUE_ENDING_Y = BLUE_SHOOTING_Y; // STARTING Y FOR TELEOP + ENDING Y FOR AUTON

    public static double BLUE_WOBBLE_X_0 = HALF_TILE;
    public static double BLUE_WOBBLE_Y_0 = ONE_HALF_TILE;

    public static double BLUE_WOBBLE_X_1 = ONE_HALF_TILE;
    public static double BLUE_WOBBLE_Y_1 = HALF_TILE;

    public static double BLUE_WOBBLE_X_4 = 2*TILE+HALF_TILE;
    public static double BLUE_WOBBLE_Y_4 = ONE_HALF_TILE;

    // from left to right

    public static double BLUE_POWERSHOT_X = BLUE_SHOOTING_X;

    public static double BLUE_POWERSHOT_Y_1 = 42;
    public static double BLUE_POWERSHOT_Y_2 = 37;
    public static double BLUE_POWERSHOT_Y_3 = 31;


    // GETS X VALUE AGAINST THE WALL
    public double againstWallValueX(double value) {
        if (value < 0) {
            return value + ROBOT_LENGTH / 2;
        } else {
            return value - ROBOT_LENGTH / 2;
        }
    }
}
