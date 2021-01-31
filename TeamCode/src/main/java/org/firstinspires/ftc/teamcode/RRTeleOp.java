package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@TeleOp(name = "RRTeleOp")
public class RRTeleOp extends LinearOpMode {

    public static double driveClip = 0.6;

//    private DcMotor leftShooter, rightShooter;

//    private DcMotor intake;

//    private Servo ringPush;

    public static double PUSH_MAX_VALUE = 0.1;
    public static double PUSH_MIN_VALUE = 0.8;

    @Override
    public void runOpMode() {

//        leftShooter = hardwareMap.get(DcMotor.class, "left_shooter");
//        rightShooter = hardwareMap.get(DcMotor.class, "right_shooter");
//
//        intake = hardwareMap.get(DcMotor.class, "intake_motor");
//
//        ringPush = hardwareMap.get(Servo.class, "ring_push");

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        waitForStart();
        while (opModeIsActive()) {
            double forward = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x;

            double frontLeftPower = Range.clip(forward + turn + strafe, -driveClip, driveClip);
            double frontRightPower = Range.clip(forward - turn - strafe, -driveClip, driveClip);
            double backLeftPower = Range.clip(forward + turn - strafe, -driveClip, driveClip);
            double backRightPower = Range.clip(forward - turn + strafe, -driveClip, driveClip);



            if (Math.abs(frontLeftPower) > 1 || Math.abs(backLeftPower) > 1 ||
                    Math.abs(frontRightPower) > 1 || Math.abs(backRightPower) > 1 ) {
                // Find the largest power
                double max = 0;
                max = Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower));
                max = Math.max(Math.abs(frontRightPower), max);
                max = Math.max(Math.abs(backRightPower), max);

                // Divide everything by max (it's positive so we don't need to worry
                // about signs)
                frontLeftPower /= max;
                backLeftPower /= max;
                frontRightPower /= max;
                backRightPower /= max;
            }

            drive.setMotorPowers(frontLeftPower,backLeftPower,frontRightPower,backRightPower);

//            if (gamepad1.a) {
//                ringPush.setPosition(PUSH_MAX_VALUE);
//            }
//
//            if (gamepad1.b) {
//                ringPush.setPosition(PUSH_MIN_VALUE);
//            }

        }
    }
}