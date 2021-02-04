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

        Pose2d startPose = new Pose2d(-48,-50,Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        waitForStart();
        if (isStopRequested()) return;
//        trajectory.forward(distance,0,0,0);

        Trajectory traj1 = drive.trajectoryBuilder(startPose)
//                .splineTo(new Vector2d(-45, 35),Math.toRadians(90))
//                .lineTo(new Vector2d(-45,35))
                .splineToConstantHeading(new Vector2d(0, 0), Math.toRadians(0))
                .build();

//        Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
////                .splineTo(new Vector2d(12, 61), Math.toRadians(90))
////                .lineTo(new Vector2d(12,61))
//                .splineToConstantHeading(new Vector2d(12, 61), Math.toRadians(90))
//                .build();



        drive.followTrajectory(traj1);
//        drive.followTrajectory(traj2);
    }
}

//@Autonomous(group = "drive")
//public class SplineTest extends LinearOpMode {
//    @Override
//    public void runOpMode() throws InterruptedException {
//        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
//
//        waitForStart();
//
//        if (isStopRequested()) return;
//
//        Trajectory traj = drive.trajectoryBuilder(new Pose2d())
//                .splineTo(new Vector2d(30, 30), 0)
//                .build();
//
//        drive.followTrajectory(traj);
//
//        sleep(2000);
//
//        drive.followTrajectory(
//                drive.trajectoryBuilder(traj.end(), true)
//                        .splineTo(new Vector2d(0, 0), Math.toRadians(180))
//                        .build()
//        );
//    }
//}
