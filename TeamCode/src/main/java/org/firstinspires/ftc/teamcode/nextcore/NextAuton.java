package org.firstinspires.ftc.teamcode.nextcore;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
public class NextAuton extends LinearOpMode {
    public double STARTING_X;
    public double STARTING_Y;

    public SampleMecanumDrive drive = null;
    public Mechanisms mech = null;
    public Pose2d startPose = null;


    public NextAuton(double STARTING_X, double STARTING_Y) {
        this.STARTING_X = STARTING_X;
        this.STARTING_Y = STARTING_Y;
    }

    @Override
    public void runOpMode() {
        drive = new SampleMecanumDrive(hardwareMap);
        mech = new Mechanisms(hardwareMap);


        startPose = new Pose2d(STARTING_X, STARTING_Y, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        waitForStart();

        if (isStopRequested()) return;
    }

    public void runTraj(Trajectories trajs) {
        Trajectories traj = trajs;
        drive.followTrajectory(traj.dropWobbleGoal);
        drive.followTrajectory(traj.goToShootOne);
        drive.followTrajectory(traj.goBackIntake);
        drive.followTrajectory(traj.goToShootTwo);
        drive.followTrajectory(traj.parkOnLine);
    }


}
