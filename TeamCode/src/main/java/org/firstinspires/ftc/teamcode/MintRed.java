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
@TeleOp(name = "T - Mint Red")
public class MintRed extends LinearOpMode {

    // FIELD SPECIFIC DATA
    public static double MAX_VALUE = 72;

    public static double TILE = 24;
    public static double HALF_TILE = TILE / 2;

    public static double TAPE_WIDTH = 2;

    public static double LAUNCH_LINE_X = 80 - MAX_VALUE + (TAPE_WIDTH / 2);

    public static double RED_SHOOTING_X = -1;
    public static double RED_SHOOTING_Y = -39;

    public static double RED_ENDING_X = LAUNCH_LINE_X; // STARTING X FOR TELEOP + ENDING X FOR AUTON
    public static double RED_ENDING_Y = RED_SHOOTING_Y; // STARTING Y FOR TELEOP + ENDING Y FOR AUTON

    // from left to right

    public static double RED_POWERSHOT_X = RED_SHOOTING_X;

    public static double RED_POWERSHOT_Y_1 = -31;
    public static double RED_POWERSHOT_Y_2 = -37;
    public static double RED_POWERSHOT_Y_3 = -42;

    enum Mode {
        DRIVER_CONTROL,
        AUTOMATIC_CONTROL
    }

    Mode currentMode = Mode.DRIVER_CONTROL;

    Vector2d SHOOTING_POSITION = new Vector2d(RED_SHOOTING_X, RED_SHOOTING_Y);

    Vector2d POWERSHOT_1 = new Vector2d(RED_POWERSHOT_X, RED_POWERSHOT_Y_1);
    Vector2d POWERSHOT_2 = new Vector2d(RED_POWERSHOT_X, RED_POWERSHOT_Y_2);
    Vector2d POWERSHOT_3 = new Vector2d(RED_POWERSHOT_X, RED_POWERSHOT_Y_3);

    double TARGET_ANGLE = Math.toRadians(0); // might need to change

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Pose2d startPose = new Pose2d(RED_ENDING_X, RED_ENDING_Y, Math.toRadians(0));
        drive.setPoseEstimate(startPose);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {
            drive.update();

            // Read pose
            Pose2d poseEstimate = drive.getPoseEstimate();

            // Print pose to telemetry
            telemetry.addData("mode", currentMode);
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();

            switch (currentMode) {
                case DRIVER_CONTROL:
                    // Gamepad 1 - Ian
                    drive.setWeightedDrivePower(
                            new Pose2d(
                                    -gamepad1.left_stick_y,
                                    -gamepad1.left_stick_x,
                                    -gamepad1.right_stick_x
                            )
                    );

                    // Top and Left Buttons
                    if (gamepad1.y) mech.runIntake(Mechanisms.intakeState.IN);
                    if (gamepad1.b) mech.runIntake(Mechanisms.intakeState.OUT);
                    if (gamepad1.y && gamepad1.b) mech.runIntake(Mechanisms.intakeState.OFF);

                    if (gamepad1.a) {
                        mech.stickOne.setPosition(1);
                        telemetry.addLine("100%");
                        telemetry.addLine();
                    }

                    if (gamepad1.x) {
                        mech.stickOne.setPosition(0.5);
                        telemetry.addLine("50%");
                        telemetry.addLine();
                    }

                    if (gamepad1.dpad_up) mech.wobbleArmControl(Mechanisms.wobbleArmPos.UP);
                    if (gamepad1.dpad_down) mech.wobbleArmControl(Mechanisms.wobbleArmPos.DOWN);
                    if (gamepad1.dpad_left) mech.wobbleArmControl(Mechanisms.wobbleArmPos.OVER);

                    if (gamepad1.right_trigger > 0.5) mech.pushRings();
                    if (gamepad1.left_trigger > 0.5) mech.pushRing();

                    if (gamepad1.left_bumper) mech.wobbleControl(Mechanisms.wobblePos.OPEN);
                    if (gamepad1.right_bumper) mech.wobbleControl(Mechanisms.wobblePos.CLOSE);

                    // Gamepad 2 - Michael
                    if (gamepad2.dpad_up) mech.setShooter(Mechanisms.motorPower.HIGH);
                    if (gamepad2.dpad_down) mech.setShooter(Mechanisms.motorPower.OFF);
                    if (gamepad2.dpad_left) mech.setShooter(Mechanisms.motorPower.STALL);

                    if (gamepad2.x) {
                        Trajectory traj = drive.trajectoryBuilder(poseEstimate)
                                .splineTo(POWERSHOT_1, TARGET_ANGLE)
                                .build();

                        drive.followTrajectoryAsync(traj);

                        currentMode = Mode.AUTOMATIC_CONTROL;

                    } else if (gamepad2.y) {
                        Trajectory traj = drive.trajectoryBuilder(poseEstimate)
                                .splineTo(POWERSHOT_2, TARGET_ANGLE)
                                .build();

                        drive.followTrajectoryAsync(traj);

                        currentMode = Mode.AUTOMATIC_CONTROL;

                    } else if (gamepad2.b) {
                        Trajectory traj = drive.trajectoryBuilder(poseEstimate)
                                .splineTo(POWERSHOT_3, TARGET_ANGLE)
                                .build();

                        drive.followTrajectoryAsync(traj);

                        currentMode = Mode.AUTOMATIC_CONTROL;

                    } else if (gamepad2.a) {
                        Trajectory traj = drive.trajectoryBuilder(poseEstimate)
                                .splineTo(SHOOTING_POSITION, TARGET_ANGLE)
                                .build();

                        drive.followTrajectoryAsync(traj);

                        currentMode = Mode.AUTOMATIC_CONTROL;
                    }

                    // TRIPLE SHOT
                    if (gamepad2.y && gamepad2.b) {
                        Trajectory traj = drive.trajectoryBuilder(poseEstimate)
                                .splineTo(SHOOTING_POSITION, TARGET_ANGLE)
                                .build();
                        Trajectory traj2 = drive.trajectoryBuilder(traj.end())
                                .lineTo(POWERSHOT_2)
                                .build();
                        Trajectory traj3 = drive.trajectoryBuilder(traj2.end())
                                .lineTo(POWERSHOT_3)
                                .build();

                        drive.followTrajectoryAsync(traj);

                        mech.wait(250);
                        mech.pushRing();
                        mech.wait(250);

                        drive.followTrajectoryAsync(traj2);

                        mech.wait(250);
                        mech.pushRing();
                        mech.wait(250);

                        drive.followTrajectoryAsync(traj3);

                        mech.wait(250);
                        mech.pushRing();
                        mech.wait(250);

                        currentMode = Mode.AUTOMATIC_CONTROL;
                    }
                    break;
                case AUTOMATIC_CONTROL:
                    // If x is pressed, we break out of the automatic following
                    if (gamepad2.start || gamepad1.start) {
                        drive.cancelFollowing();
                        currentMode = Mode.DRIVER_CONTROL;
                    }

                    // If drive finishes its task, cede control to the driver
                    if (!drive.isBusy()) {
                        currentMode = Mode.DRIVER_CONTROL;
                    }
                    break;
            }

        }
    }

}
