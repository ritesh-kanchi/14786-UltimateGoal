package org.firstinspires.ftc.teamcode.nextcore;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


@Config
public class NextTeleOp extends LinearOpMode {

    public double STARTING_X;
    public double STARTING_Y;

    enum Mode {
        DRIVER_CONTROL,
        AUTOMATIC_CONTROL
    }

    Mode currentMode = Mode.DRIVER_CONTROL;

    public static double TARGET_HEADING = Math.toRadians(0);


    public SampleMecanumDrive drive = null;
    public Mechanisms mech = null;

    public NextTeleOp(double STARTING_X, double STARTING_Y) {
        this.STARTING_X = STARTING_X;
        this.STARTING_Y = STARTING_Y;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        mech = new Mechanisms(hardwareMap);

        Pose2d startPose = new Pose2d(STARTING_X, STARTING_Y, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        mech.runtime.reset();
    }

    public void runControls() {
        // Mechanisms - GAMEPAD 1
        if (gamepad1.a) mech.wobbleControl(Mechanisms.wobblePos.CLOSE);

        if (gamepad1.b) mech.wobbleControl(Mechanisms.wobblePos.OPEN);

        if (gamepad1.x) mech.runIntake(Mechanisms.intakeState.IN);

        if (gamepad1.y) mech.runIntake(Mechanisms.intakeState.OFF);

        if (gamepad1.left_bumper) mech.setShooter(Mechanisms.motorPower.HIGH);

        if (gamepad1.right_bumper) mech.setShooter(Mechanisms.motorPower.OFF);

        if (gamepad1.right_trigger > 0.5) mech.pushRings();
        if (gamepad1.left_trigger > 0.5) mech.pushRing();

        if (gamepad2.dpad_up) mech.wobbleArmControl(Mechanisms.wobbleArmPos.UP);

        if (gamepad2.dpad_down) mech.wobbleArmControl(Mechanisms.wobbleArmPos.DOWN);


        // idk what this is
        if (gamepad2.dpad_left) {
            mech.stickOne.setPosition(1);
            telemetry.addLine("eeeeeee");
            telemetry.addLine();
        }

        if (gamepad2.dpad_right) {
            mech.stickOne.setPosition(0.5);
            telemetry.addLine("aaaaa");
            telemetry.addLine();
        }
    }

    public void runDrive() {
        // Mecanum Drive Control
        drive.setWeightedDrivePower(
                new Pose2d(
                        Range.clip(-gamepad1.left_stick_y, -0.8, 0.8),
                        Range.clip(-gamepad1.left_stick_x, -0.8, 0.8),
                        Range.clip(-gamepad1.right_stick_x, -0.8, 0.8)
                )
        );



    }

    public void driveData(Pose2d poseEstimate) {
        drive.update();


        telemetry.addData("x", poseEstimate.getX());
        telemetry.addData("y", poseEstimate.getY());
        telemetry.addData("heading", poseEstimate.getHeading());
        telemetry.addLine();
        telemetry.addData("S1", mech.shooterOne.getVelocity());
        telemetry.addData("S2", mech.shooterTwo.getVelocity());
        telemetry.update();
    }

    public void autoControl(Pose2d poseEstimate, Vector2d SHOOTING_POSITION,Vector2d POWERSHOT_1,Vector2d POWERSHOT_2,Vector2d POWERSHOT_3) {
        switch (currentMode) {
            case DRIVER_CONTROL:
                runDrive();
                runControls();

                if (gamepad2.a) {
                    Trajectory toShoot = drive.trajectoryBuilder(poseEstimate)
                            .splineTo(SHOOTING_POSITION, TARGET_HEADING)
                            .build();
                    drive.followTrajectory(toShoot);

                    currentMode = Mode.AUTOMATIC_CONTROL;
                } else if (gamepad2.x) {
                    Trajectory toLeftShot = drive.trajectoryBuilder(poseEstimate)
                            .splineTo(POWERSHOT_1, TARGET_HEADING)
                            .build();
                    drive.followTrajectory(toLeftShot);

                    currentMode = Mode.AUTOMATIC_CONTROL;
                } else if (gamepad2.y) {
                    Trajectory toMiddleShot = drive.trajectoryBuilder(poseEstimate)
                            .splineTo(POWERSHOT_2, TARGET_HEADING)
                            .build();
                    drive.followTrajectory(toMiddleShot);

                    currentMode = Mode.AUTOMATIC_CONTROL;
                } else if (gamepad2.b) {
                    Trajectory toRightShot = drive.trajectoryBuilder(poseEstimate)
                            .splineTo(POWERSHOT_3, TARGET_HEADING)
                            .build();
                    drive.followTrajectory(toRightShot);

                    currentMode = Mode.AUTOMATIC_CONTROL;
                }
                break;
            case AUTOMATIC_CONTROL:
                if (gamepad2.left_trigger > 0) {
                    drive.cancelFollowing();
                    currentMode = Mode.DRIVER_CONTROL;
                }

                if (!drive.isBusy()) {
                    currentMode = Mode.DRIVER_CONTROL;
                }
                break;

        }
    }
}
