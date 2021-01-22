package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@Autonomous(name = "TestTrajectory")
public class TestTrajectory extends LinearOpMode {

    public static double distance = 10;

    @Override
    public void runOpMode() {
        SampleMecanumDrive driveTrain = new SampleMecanumDrive(hardwareMap);

        Trajectories trajectory = new Trajectories(driveTrain,this);

        waitForStart();
        trajectory.forwardT(distance,0,0,0);
    }
}
