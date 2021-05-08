package org.firstinspires.ftc.teamcode.nextcore;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class Trajectories {

    SampleMecanumDrive drive = null;
    Mechanisms mech = null;
    Pose2d startPose = null;

    double WOBBLE_X,WOBBLE_Y,SHOOTING_X,SHOOTING_Y,ENDING_X,ENDING_Y;

    public static double STRAFE_LEFT_TO_RING = 13;
    public static double INTAKE_BACK = 35;

    public Trajectories(SampleMecanumDrive drive, Mechanisms mech, Pose2d startPose, double WOBBLE_X, double WOBBLE_Y, double SHOOTING_X, double SHOOTING_Y, double ENDING_X, double ENDING_Y) {
this.drive = drive;
this.mech = mech;
this.startPose = startPose;
this.WOBBLE_X = WOBBLE_X;
this.WOBBLE_Y = WOBBLE_Y;
this.SHOOTING_X = SHOOTING_X;
this.SHOOTING_Y = SHOOTING_Y;
this.ENDING_X = ENDING_X;
this.ENDING_Y = ENDING_Y;
    }

    public Trajectory dropWobbleGoal = drive.trajectoryBuilder(startPose)
            .splineToConstantHeading(new Vector2d(-25, 0), Math.toRadians(0))
            .splineToConstantHeading(new Vector2d(WOBBLE_X, WOBBLE_Y), Math.toRadians(0))
            .addDisplacementMarker(() -> {
                mech.setShooter(Mechanisms.motorPower.HIGH);
            })
            .build();

    public Trajectory goToShootOne = drive.trajectoryBuilder(dropWobbleGoal.end())
            .splineToConstantHeading(new Vector2d(SHOOTING_X, SHOOTING_Y), Math.toRadians(0))
            .build();

    public Trajectory getNewRings = drive.trajectoryBuilder(goToShootOne.end())
            .strafeLeft(STRAFE_LEFT_TO_RING)
            .addDisplacementMarker(() -> {
                mech.setShooter(Mechanisms.motorPower.STALL);
                mech.runIntake(Mechanisms.intakeState.IN);
            })
            .build();
    //
    public  Trajectory goBackIntake = drive.trajectoryBuilder(getNewRings.end())
            .back(INTAKE_BACK)
            .build();

    public Trajectory goToShootTwo = drive.trajectoryBuilder(goBackIntake.end())
            .addDisplacementMarker(() -> {
                mech.runIntake(Mechanisms.intakeState.OFF);
                mech.setShooter(Mechanisms.motorPower.HIGH);
            })
            .lineTo(new Vector2d(SHOOTING_X, SHOOTING_Y))
            .build();

    public Trajectory parkOnLine = drive.trajectoryBuilder(goToShootTwo.end())
            .addDisplacementMarker(() -> {
                mech.setShooter(Mechanisms.motorPower.HIGH);
            })
            .lineTo(new Vector2d(ENDING_X, ENDING_Y))
            .build();
}
