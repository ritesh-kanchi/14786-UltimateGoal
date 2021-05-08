package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;

@Config
@TeleOp(name = "Rocky Road TeleOp")
// ADVANCED EXPERIMENTAL CONTROL SCHEME
public class RockyTeleOp extends LinearOpMode {

    private Mechanisms.motorPower shooterPower = Mechanisms.motorPower.OFF;

    private static int SHOOT_RANGE = 5;
    double targetAngle = Math.toRadians(0);

    public static double SHOOTING_X = 0;
    public static double SHOOTING_Y = -35;

    public static double POWSHOT1_X = 0;
    public static double POWSHOT1_Y = -12;

    public static double STARTING_X = 10;
    public static double STARTING_Y = -35;

    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);

        Pose2d startPose = new Pose2d(STARTING_X, STARTING_Y, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Servo stickOne = hardwareMap.get(Servo.class, "stickOne");

        waitForStart();
        mech.runtime.reset();
        while (opModeIsActive()) {

            double testPos = 0.45;

            Pose2d poseEstimate = drive.getPoseEstimate();

            // Mecanum Drive Control
            drive.setWeightedDrivePower(
                    new Pose2d(
                            Range.clip(-gamepad1.left_stick_y, -0.8, 0.8),
                            Range.clip(-gamepad1.left_stick_x, -0.8, 0.8),
                            Range.clip(-gamepad1.right_stick_x, -0.8, 0.8)
                    )
            );

            drive.update();

            /*

             */


            // Mechanisms
            if (gamepad1.a) mech.wobbleControl(Mechanisms.wobblePos.CLOSE);

            if (gamepad1.b) mech.wobbleControl(Mechanisms.wobblePos.OPEN);

            if (gamepad1.x) mech.runIntake(Mechanisms.intakeState.IN);

            if (gamepad1.y) mech.runIntake(Mechanisms.intakeState.OFF);

            if (gamepad1.left_bumper) mech.setShooter(Mechanisms.motorPower.HIGH);

            if (gamepad1.right_bumper) mech.setShooter(Mechanisms.motorPower.OFF);

            if (gamepad1.right_trigger > 0.5) mech.pushRings();

            if (gamepad1.left_trigger > 0.5) {

                Trajectory setShootingPosition = drive.trajectoryBuilder(poseEstimate)
                        .splineTo(new Vector2d(SHOOTING_X, SHOOTING_Y), targetAngle)
                        .build();

                drive.followTrajectoryAsync(setShootingPosition);

            }

            //if (gamepad1.dpad_up) mech.wobbleArmControl(Mechanisms.wobbleArmPos.UP);

            //if (gamepad1.dpad_down) mech.wobbleArmControl(Mechanisms.wobbleArmPos.DOWN);

            //if (gamepad1.dpad_left) mech.wobbleControl(Mechanisms.wobblePos.OPEN);

            //if (gamepad1.dpad_right) mech.wobbleControl(Mechanisms.wobblePos.CLOSE);

            if (gamepad2.dpad_up) mech.wobbleArmControl(Mechanisms.wobbleArmPos.UP);

            if (gamepad2.dpad_down) mech.wobbleArmControl(Mechanisms.wobbleArmPos.DOWN);

            if (gamepad2.dpad_left) mech.wobbleControl(Mechanisms.wobblePos.OPEN);

            if (gamepad2.dpad_right) mech.wobbleControl(Mechanisms.wobblePos.CLOSE);

            if (gamepad2.b) mech.wobbleTurretControl(Mechanisms.wobbleTurretPos.IN);

            if (gamepad2.a) mech.wobbleTurretControl(Mechanisms.wobbleTurretPos.OUT);

            //if (gamepad2.dpad_left) mech.wobbleTurretControl(Mechanisms.wobbleTurretPos.IN);

            //if (gamepad2.dpad_right) mech.wobbleTurretControl(Mechanisms.wobbleTurretPos.OUT);

            if (gamepad2.y) {
                stickOne.setPosition(1);
                telemetry.addLine("eeeeeee");
                telemetry.addLine();
            }

            if (gamepad2.x) {
                stickOne.setPosition(0.5);
                telemetry.addLine("aaaaa");
                telemetry.addLine();
            }
            if (gamepad2.left_bumper) {
                Trajectory powershot = drive.trajectoryBuilder(poseEstimate)
                        .splineTo(new Vector2d(POWSHOT1_X, POWSHOT1_Y), targetAngle)

                        .build();


                drive.followTrajectoryAsync(powershot);
            }


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
            telemetry.addLine();
            telemetry.addData("S1", mech.shooterOne.getVelocity());
            telemetry.addData("S2", mech.shooterTwo.getVelocity());
            telemetry.update();
        }
    }
}