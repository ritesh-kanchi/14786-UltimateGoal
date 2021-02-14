package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@TeleOp(name = "Vanilla")
// BASIC CONTROL SCHEME
public class VanillaTeleOp extends LinearOpMode {

    private double shooterPower = 0.5;

    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);

        Pose2d startPose = new Pose2d(-48, -50, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        mech.runtime.reset();
        while (opModeIsActive()) {

            // Mecanum Drive Control
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            drive.update();

            // Mechanisms
            if (gamepad1.a) mech.pushRing();

            if (gamepad1.dpad_left) mech.moveIntake();

            if (gamepad1.dpad_up) shooterPower += 0.1;
            if (gamepad1.dpad_down) shooterPower -= 0.1;

            mech.intake.setPower(gamepad1.right_trigger - gamepad1.left_trigger);

            if(shooterPower > 1) shooterPower = 1;

            if (shooterPower < 0) shooterPower = 0;

            // Pose Data
            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();
        }
    }
}