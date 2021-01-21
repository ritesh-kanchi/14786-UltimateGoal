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

    public void forwardT(double distance, double cX, double cY, double cH) {
        Trajectory forward = driveTrain.trajectoryBuilder(new Pose2d(cX,cY,cH))
                .forward(distance)
                .build();
    }

}
