package org.firstinspires.ftc.teamcode.nextcore;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
public class NextAuton extends LinearOpMode {
    public double STARTING_X;
    public double STARTING_Y;

    public static int WOBBLE_WAIT = 2000;

    public static int SHOOT_WAIT = 1000;

    public SampleMecanumDrive drive = null;
    public Mechanisms mech = null;
    public Pose2d startPose = null;

    public static double ANGLE_SCALE = 16/15;

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
        drive.followTrajectory(trajs.dropWobbleGoal);

        mech.wobbleArmControl(Mechanisms.wobbleArmPos.DOWN);
        mech.wait(WOBBLE_WAIT);
        mech.wobbleControl(Mechanisms.wobblePos.OPEN);

        lastHalf(trajs);


    }

    public void runTrajFLIP(Trajectories trajs) {
        drive.followTrajectory(trajs.dropWobbleGoal);
        drive.turn(Math.toRadians(180*ANGLE_SCALE));

        mech.wobbleArmControl(Mechanisms.wobbleArmPos.DOWN);
        mech.wait(WOBBLE_WAIT);
        mech.wobbleControl(Mechanisms.wobblePos.OPEN);

        drive.turn(Math.toRadians(180*ANGLE_SCALE));


        lastHalf(trajs);
    }

    public void lastHalf(Trajectories trajs) {
        drive.followTrajectory(trajs.goToShootOne);

        mech.pushRings();

        mech.wait(SHOOT_WAIT);

        drive.followTrajectory(trajs.getNewRings);

        mech.wait(SHOOT_WAIT);

        drive.followTrajectory(trajs.goToShootTwo);

        mech.wait(SHOOT_WAIT);

        mech.pushRings();

        drive.followTrajectory(trajs.parkOnLine);
    }


}
