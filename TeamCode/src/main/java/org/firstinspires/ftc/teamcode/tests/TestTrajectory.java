package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@Disabled
@Autonomous(name = "TestTrajectory")
public class TestTrajectory extends LinearOpMode {

    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);

        Pose2d startPose = new Pose2d(-48, -50, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        waitForStart();
        if (isStopRequested()) return;

        Trajectory traj1 = drive.trajectoryBuilder(startPose)
                .splineToConstantHeading(new Vector2d(0, 0), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(24, -48), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(48, 0), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(-48, -50), Math.toRadians(0))
                .build();
//
//        Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
//                .splineToConstantHeading(new Vector2d(0, 0), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(24, -48), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(48, 0), Math.toRadians(0))
//                .splineToConstantHeading(new Vector2d(-48, -50), Math.toRadians(0))
//                .build();

        drive.followTrajectory(traj1);
//        drive.followTrajectory(traj2);
    }
}