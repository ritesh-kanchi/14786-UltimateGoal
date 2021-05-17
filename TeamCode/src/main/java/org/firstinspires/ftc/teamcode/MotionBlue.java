package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;

@Config
@Autonomous(name = "Motion Blue")
public class MotionBlue extends LinearOpMode {

    // CHANGE FOR WOBBLE
    public static int WOBBLE_OPTION = 0; // 0,1,4


    // FIELD SPECIFIC DATA
    public static double MAX_VALUE = 72;
    public double MIN_VALUE = -MAX_VALUE;

    public static double TILE = 24;
    public static double HALF_TILE = TILE / 2;
    public static double ONE_HALF_TILE = TILE + HALF_TILE;

    public static double ANGLE_SCALE = 16 / 15;

    public static double TAPE_WIDTH = 2;

    // launch line ~ 80 from audience perimeter wall, tape width is 2, want to be on the middle
    public static double LAUNCH_LINE_X = 80 - MAX_VALUE + (TAPE_WIDTH / 2);

    // ROBOT SPECIFIC DATA
    public static double ROBOT_LENGTH = 18; // LENGTH OF WHEELS SIDE
    public static double ROBOT_WIDTH = ROBOT_LENGTH; // LENGTH OF INTAKE SIDE

    // BLUE SPECIFIC DATA
    public double BLUE_STARTING_X = -63;  // STARTING X FOR AUTON
    public static double BLUE_STARTING_Y = 49.5;  // STARTING Y FOR AUTON

    public static double BLUE_JUNCTION_X = -12;
    public static double BLUE_JUNCTION_Y = 12;

    public static double BLUE_SHOOTING_X = -1;
    public static double BLUE_SHOOTING_Y = 39;

    public static double BLUE_ENDING_X = LAUNCH_LINE_X; // STARTING X FOR TELEOP + ENDING X FOR AUTON
    public static double BLUE_ENDING_Y = BLUE_SHOOTING_Y; // STARTING Y FOR TELEOP + ENDING Y FOR AUTON

    public static double BLUE_WOBBLE_X_0 = HALF_TILE;
    public static double BLUE_WOBBLE_Y_0 = ONE_HALF_TILE;

    public static double BLUE_WOBBLE_X_1 = ONE_HALF_TILE;
    public static double BLUE_WOBBLE_Y_1 = HALF_TILE;

    public static double BLUE_WOBBLE_X_4 = 2 * TILE + HALF_TILE;
    public static double BLUE_WOBBLE_Y_4 = ONE_HALF_TILE;

    public double BLUE_WOBBLE_X, BLUE_WOBBLE_Y;

    // from left to right

    public static double BLUE_POWERSHOT_X = BLUE_SHOOTING_X;

    public static double BLUE_POWERSHOT_Y_1 = 42;
    public static double BLUE_POWERSHOT_Y_2 = 37;
    public static double BLUE_POWERSHOT_Y_3 = 31;

    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);

        Pose2d startPose = new Pose2d(BLUE_STARTING_X, BLUE_ENDING_Y, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        waitForStart();
        if (isStopRequested()) return;

        if (WOBBLE_OPTION == 0) {
            BLUE_WOBBLE_X = BLUE_WOBBLE_X_0;
            BLUE_WOBBLE_Y = BLUE_WOBBLE_Y_0;
        } else if (WOBBLE_OPTION == 1) {
            BLUE_WOBBLE_X = BLUE_WOBBLE_X_1;
            BLUE_WOBBLE_Y = BLUE_WOBBLE_Y_1;
        } else {
            BLUE_WOBBLE_X = BLUE_WOBBLE_X_4;
            BLUE_WOBBLE_Y = BLUE_WOBBLE_Y_4;
        }

        Trajectory dropWobble = drive.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(BLUE_JUNCTION_X, BLUE_JUNCTION_Y), Math.toRadians(0))
                .lineTo(new Vector2d(BLUE_WOBBLE_X, BLUE_WOBBLE_Y))
                .build();

        Trajectory shootRings1 = drive.trajectoryBuilder(dropWobble.end())
                .lineTo(new Vector2d(BLUE_SHOOTING_X, BLUE_SHOOTING_Y))
                .build();


        Trajectory intakeRings = drive.trajectoryBuilder(shootRings1.end())
                .back(2 * TILE)
                .build();

        Trajectory shootRings2 = drive.trajectoryBuilder(intakeRings.end())
                .lineTo(new Vector2d(BLUE_SHOOTING_X, BLUE_SHOOTING_Y))
                .build();


        // ONLY FOR 0 RINGS
        Trajectory powerShot1 = drive.trajectoryBuilder(shootRings1.end())
                .lineTo(new Vector2d(BLUE_POWERSHOT_X, BLUE_POWERSHOT_Y_1))
                .build();

        Trajectory powerShot2 = drive.trajectoryBuilder(powerShot1.end())
                .lineTo(new Vector2d(BLUE_POWERSHOT_X, BLUE_POWERSHOT_Y_2))
                .build();

        Trajectory powerShot3 = drive.trajectoryBuilder(powerShot2.end())
                .lineTo(new Vector2d(BLUE_POWERSHOT_X, BLUE_POWERSHOT_Y_3))
                .build();


        drive.followTrajectory(dropWobble);
    // INJECT TURNS
        drive.followTrajectory(shootRings1);
        if (WOBBLE_OPTION == 0) {
            drive.followTrajectory(powerShot1);
            drive.followTrajectory(powerShot2);
            drive.followTrajectory(powerShot3);
            drive.followTrajectory(robotPark(powerShot3, drive));
        } else {
            drive.followTrajectory(intakeRings);
            drive.followTrajectory(shootRings2);
            drive.followTrajectory(robotPark(shootRings2, drive));
        }


    }

    public Trajectory robotPark(Trajectory traj, SampleMecanumDrive drive) {
        Trajectory traj1 = drive.trajectoryBuilder(traj.end())
                .splineTo(new Vector2d(BLUE_ENDING_X, BLUE_STARTING_Y), Math.toRadians(0))
                .build();

        return traj1;
    }
}
