package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@Autonomous(name = "TestTrajectory")
public class TestTrajectory extends LinearOpMode {

    public static double distance = 10;

    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectories trajectory = new Trajectories(drive,this);

        Pose2d startPose = new Pose2d(-60,50,Math.toRadians(90));

        drive.setPoseEstimate(startPose);

//        waitForStart();
//        trajectory.forward(distance,0,0,0);

        Trajectory traj1 = drive.trajectoryBuilder(startPose)
//                .splineTo(new Vector2d(-45, 35),Math.toRadians(90))
//                .lineTo(new Vector2d(-45,35))
                .splineToConstantHeading(new Vector2d(-45, 35), Math.toRadians(90))
                .build();

        Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
//                .splineTo(new Vector2d(12, 61), Math.toRadians(90))
//                .lineTo(new Vector2d(12,61))
                .splineToConstantHeading(new Vector2d(12, 61), Math.toRadians(90))
                .build();



        drive.followTrajectory(traj1);
        drive.followTrajectory(traj2);
    }
}
