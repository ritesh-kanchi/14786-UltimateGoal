package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

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

        Pose2d startPose = new Pose2d(-48, -50, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        while (opModeIsActive()) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            drive.update();

//            if (gamepad1.a) {
//                ringPush.setPosition(PUSH_MAX_VALUE);
//            }
//
//            if (gamepad1.b) {
//                ringPush.setPosition(PUSH_MIN_VALUE);
//            }

            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();
        }
    }
}