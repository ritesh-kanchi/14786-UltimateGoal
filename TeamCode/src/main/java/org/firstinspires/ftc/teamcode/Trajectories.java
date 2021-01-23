package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class Trajectories {

    SampleMecanumDrive driveTrain = null;
    LinearOpMode opMode = null;

    public Trajectories(SampleMecanumDrive dt, LinearOpMode aopmode) {
        driveTrain = dt;
        opMode = aopmode;
    }

    public void basePose() {
        Trajectory base = driveTrain.trajectoryBuilder(new Pose2d())
                .build();
        fTrajectory(base);
    }

    public void basePose(double cX, double cY, double cH) {
        Trajectory base = driveTrain.trajectoryBuilder(new Pose2d(cX,cY,cH))
                .build();
        fTrajectory(base);
    }

    public void forward(double distance) {
        Trajectory forward = driveTrain.trajectoryBuilder(new Pose2d())
                .forward(distance)
                .build();
        fTrajectory(forward);
    }

    public void forward(double distance, double cX, double cY, double cH) {
        Trajectory forward = driveTrain.trajectoryBuilder(new Pose2d(cX,cY,cH))
                .forward(distance)
                .build();
        fTrajectory(forward);
    }

    public void backward(double distance) {
        Trajectory backward = driveTrain.trajectoryBuilder(new Pose2d())
                .back(distance)
                .build();
        fTrajectory(backward);
    }

    public void backward(double distance, double cX, double cY, double cH) {
        Trajectory backward = driveTrain.trajectoryBuilder(new Pose2d(cX,cY,cH))
                .back(distance)
                .build();
        fTrajectory(backward);
    }

    public void strafeRight(double distance) {
        Trajectory right = driveTrain.trajectoryBuilder(new Pose2d())
                .strafeRight(distance)
                .build();
        fTrajectory(right);
    }

    public void strafeRight(double distance, double cX, double cY, double cH) {
        Trajectory right = driveTrain.trajectoryBuilder(new Pose2d(cX,cY,cH))
                .strafeRight(distance)
                .build();
        fTrajectory(right);
    }

    public void strafeLeft(double distance) {
        Trajectory left = driveTrain.trajectoryBuilder(new Pose2d())
                .strafeLeft(distance)
                .build();
        fTrajectory(left);
    }

    public void strafeLeft(double distance, double cX, double cY, double cH) {
        Trajectory left = driveTrain.trajectoryBuilder(new Pose2d(cX,cY,cH))
                .strafeLeft(distance)
                .build();
        fTrajectory(left);
    }

    private void fTrajectory(Trajectory traj) {
        driveTrain.followTrajectory(traj);
    }
}
