package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;

@Config
@TeleOp(name = "Mint TeleOp")
public class MintTeleOp extends LinearOpMode {

    enum DriveMode {
        DRIVER_CONTROL,
        AUTOMATIC_CONTROL
    }

    public static double SHOOTING_X = 0;
    public static double SHOOTING_Y = -35;

    DriveMode currentMode = DriveMode.DRIVER_CONTROL;

    double targetAngle = Math.toRadians(0);

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Pose2d startPose = new Pose2d(10, -35, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {
            drive.update();

            Pose2d poseEstimate = drive.getPoseEstimate();

            telemetry.addData("Mode", currentMode);
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();

            switch (currentMode) {
                case DRIVER_CONTROL:


                    drive.setWeightedDrivePower(
                            new Pose2d(
                                    -gamepad1.left_stick_y,
                                    -gamepad1.left_stick_x,
                                    -gamepad1.right_stick_x
                            )
                    );

                    drive.update();

                    // Mechanisms
                    if (gamepad1.a) {
                        mech.pushRings();
                    } else if (gamepad1.x) {
                        mech.runIntake(Mechanisms.intakeState.IN);
                    } else if (gamepad1.y) {
                        mech.runIntake(Mechanisms.intakeState.OFF);
                    } else if (gamepad1.dpad_up) {
                        mech.setShooter(Mechanisms.motorPower.HIGH);
                    } else if (gamepad1.dpad_down) {
                        mech.setShooter(Mechanisms.motorPower.STALL);
                    } else if (gamepad1.dpad_right) {
                        mech.setShooter(Mechanisms.motorPower.OFF);
                    } else if (gamepad1.left_bumper && gamepad1.right_bumper) {
                        Trajectory traj = drive.trajectoryBuilder(poseEstimate)
                                .splineTo(new Vector2d(SHOOTING_X, SHOOTING_Y), targetAngle)
                                .build();

                        drive.followTrajectoryAsync(traj);

                        currentMode = DriveMode.AUTOMATIC_CONTROL;
                    }

                    break;
                case AUTOMATIC_CONTROL:

                    if (gamepad1.left_bumper || gamepad1.right_bumper) {
                        drive.cancelFollowing();
                        currentMode = DriveMode.DRIVER_CONTROL;
                    }

                    if (!drive.isBusy()) {
                        currentMode = DriveMode.DRIVER_CONTROL;
                    }
                    break;
            }
        }
    }
}
