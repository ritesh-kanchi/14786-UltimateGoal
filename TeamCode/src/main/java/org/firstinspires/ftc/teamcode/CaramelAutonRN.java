package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;

@Config
@Disabled
@Autonomous(name = "CaramelAutonRN")
public class CaramelAutonRN extends LinearOpMode {

    // Position Coordinates
    public static double STARTING_X = -63;
    public static double STARTING_Y = -25;

    public static double SHOOTING_X = 0;
    public static double SHOOTING_Y = -35;


    public static double QUAD_RING_X = 50;
    public static double QUAD_RING_Y = -50;

    public static double SINGLE_RING_X = 25;
    public static double SINGLE_RING_Y = -25;

    public static double NO_RING_X = 0;
    public static double NO_RING_Y = -50;

    private double WOBBLE_X = QUAD_RING_X;
    private double WOBBLE_Y = QUAD_RING_Y;

    public static double LINE_X = 10;
    public static double LINE_Y = -35;

    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);


        Pose2d startPose = new Pose2d(STARTING_X, STARTING_Y, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        waitForStart();

        if (isStopRequested()) return;


        Trajectory goToShootOne = drive.trajectoryBuilder(startPose)
                .addDisplacementMarker(() -> {
                    mech.setShooter(Mechanisms.motorPower.STALL);
                })
                .splineToConstantHeading(new Vector2d(-25, 0), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(SHOOTING_X, SHOOTING_Y), Math.toRadians(0))
                .addDisplacementMarker(() -> {
                    mech.setShooter(Mechanisms.motorPower.HIGH);
//                        mech.pushRings();
                    mech.setShooter(Mechanisms.motorPower.OFF);
                })
                .build();

        Trajectory placeWobble = drive.trajectoryBuilder(goToShootOne.end())
                .splineToConstantHeading(new Vector2d(WOBBLE_X, WOBBLE_Y), Math.toRadians(0))
                .addDisplacementMarker(() -> {
                    mech.wobbleControl(Mechanisms.wobblePos.OPEN);
                    mech.wait(1000);
                    mech.wobbleControl(Mechanisms.wobblePos.CLOSE);
                })
                .build();

        Trajectory getNewRings = drive.trajectoryBuilder(placeWobble.end())
                .splineToConstantHeading(new Vector2d(SHOOTING_X, SHOOTING_Y), Math.toRadians(0))
                .addDisplacementMarker(() -> {
//                    mech.runIntake(Mechanisms.motorPower.HIGH);
                })


                .build();
//
        Trajectory goBackIntake = drive.trajectoryBuilder(getNewRings.end())
                .back(35)
                .build();

        Trajectory goToShootTwo = drive.trajectoryBuilder(goBackIntake.end())
                .addDisplacementMarker(() -> {
//                    mech.runIntake(Mechanisms.motorPower.OFF);
                    mech.setShooter(Mechanisms.motorPower.STALL);
                })
                .forward(35)
                .addDisplacementMarker(() -> {
                    mech.setShooter(Mechanisms.motorPower.HIGH);
//                        mech.pushRings();
                    mech.setShooter(Mechanisms.motorPower.OFF);
                })
                .build();

        Trajectory parkOnLine = drive.trajectoryBuilder(goToShootTwo.end())
                .forward(10)
                .build();

        // Follow trajectories in order, may need to place functions
        // between these instead of using markers
        drive.followTrajectory(goToShootOne);
        drive.followTrajectory(placeWobble);
        drive.followTrajectory(getNewRings);
        drive.followTrajectory(goBackIntake);
        drive.followTrajectory(goToShootTwo);
        drive.followTrajectory(parkOnLine);
    }
}
