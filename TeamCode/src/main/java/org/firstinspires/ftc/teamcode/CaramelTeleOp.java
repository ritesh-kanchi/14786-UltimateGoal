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
@TeleOp(name = "Caramel TeleOp")
// ADVANCED EXPERIMENTAL CONTROL SCHEME
public class CaramelTeleOp extends LinearOpMode {

    private Mechanisms.motorPower shooterPower = Mechanisms.motorPower.OFF;

    private static int SHOOT_RANGE = 5;

    private static double SHOOTING_X = 0;

    private static double SHOOTING_Y = -35;

    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);

        Pose2d startPose = new Pose2d(10, -35, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        mech.runtime.reset();
        while (opModeIsActive()) {

            Pose2d poseEstimate = drive.getPoseEstimate();

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
            if (gamepad1.a) mech.pushRings();
//
//            if (gamepad1.dpad_left) mech.moveIntake();
//
            if (gamepad1.dpad_up) mech.setShooter(Mechanisms.motorPower.HIGH);

            if (gamepad1.dpad_down) mech.setShooter(Mechanisms.motorPower.STALL);

            if (gamepad1.dpad_right) mech.setShooter(Mechanisms.motorPower.OFF);
//
//            mech.setShooter(shooterPower);
//
//            mech.intakeControl(intakePower, gamepad1);

            // TESTING TOGGLES
//            boolean xOn = false;
//            if (gamepad1.x && xOn == false) {
//                telemetry.addLine("Toggle Off");
//                xOn = true;
//            } else if (gamepad1.x && xOn) {
//                telemetry.addLine(" Toggle On");
//                xOn = false;
//            }


            if ((poseEstimate.getX() < SHOOTING_X + SHOOT_RANGE
                    && poseEstimate.getX() > SHOOTING_X - SHOOT_RANGE)
                    && (poseEstimate.getY() < SHOOTING_Y + SHOOT_RANGE
                    && poseEstimate.getY() > SHOOTING_Y - SHOOT_RANGE)) {
                telemetry.addLine("READY TO SHOOT, WITHIN RANGE OF TARGET");
                telemetry.addLine();
            }

            // Pose Data
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();
        }
    }
}