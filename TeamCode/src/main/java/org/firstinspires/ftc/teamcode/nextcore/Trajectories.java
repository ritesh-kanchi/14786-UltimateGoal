package org.firstinspires.ftc.teamcode.nextcore;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class Trajectories {

    SampleMecanumDrive drive = null;
    Mechanisms mech = null;
    Pose2d startPose = null;

    Positions pos = new Positions();

    double JUNCTION_X,JUNCTION_Y,WOBBLE_X, WOBBLE_Y, SHOOTING_X, SHOOTING_Y, ENDING_X, ENDING_Y;


    public Trajectories(SampleMecanumDrive drive, Mechanisms mech, Pose2d startPose, double JUNCTION_X, double JUNCTION_Y, double WOBBLE_X, double WOBBLE_Y, double SHOOTING_X, double SHOOTING_Y, double ENDING_X, double ENDING_Y) {
        this.drive = drive;
        this.mech = mech;
        this.startPose = startPose;
        this.JUNCTION_X = JUNCTION_X;
        this.JUNCTION_Y =JUNCTION_Y;
        this.WOBBLE_X = WOBBLE_X;
        this.WOBBLE_Y = WOBBLE_Y;
        this.SHOOTING_X = SHOOTING_X;
        this.SHOOTING_Y = SHOOTING_Y;
        this.ENDING_X = ENDING_X;
        this.ENDING_Y = ENDING_Y;
    }

    public Trajectory dropWobbleGoal = drive.trajectoryBuilder(startPose)
            .splineToConstantHeading(new Vector2d(JUNCTION_X, JUNCTION_Y), Math.toRadians(0))
            .lineTo(new Vector2d(WOBBLE_X, WOBBLE_Y))
            .addDisplacementMarker(() -> {
                mech.setShooter(Mechanisms.motorPower.HIGH);
            })
            .build();

    // FLIPPED VERSION
    public Trajectory dropWobbleGoalFLIP = drive.trajectoryBuilder(startPose)
            .splineToConstantHeading(new Vector2d(JUNCTION_X, JUNCTION_Y), Math.toRadians(0))
            .lineTo(new Vector2d(WOBBLE_X, WOBBLE_Y))
            .addDisplacementMarker(() -> {
                mech.setShooter(Mechanisms.motorPower.HIGH);
            })
            .build();

    public Trajectory goToShootOne = drive.trajectoryBuilder(dropWobbleGoal.end())
            .lineTo(new Vector2d(SHOOTING_X, SHOOTING_Y))
            .addDisplacementMarker(() -> {
                mech.runIntake(Mechanisms.intakeState.IN);
            })
            .build();

    public Trajectory getNewRings = drive.trajectoryBuilder(goToShootOne.end())
            .back(pos.ONE_HALF_TILE)
            .build();

    public Trajectory goToShootTwo = drive.trajectoryBuilder(getNewRings.end())
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
