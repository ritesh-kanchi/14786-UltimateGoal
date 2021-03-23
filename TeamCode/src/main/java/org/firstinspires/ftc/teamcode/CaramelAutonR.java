package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;
import org.firstinspires.ftc.teamcode.nextcore.ObjectDetection;

import java.util.List;

@Config
@Autonomous(name = "CaramelAutonR")
public class CaramelAutonR extends LinearOpMode {

    // Position Coordinates
    public static double STARTING_X = -63;
    public static double STARTING_Y = -25;

    public static double ENDING_X = 10;
    public static double ENDING_Y = -35;

    public static double SHOOTING_X = -3;
    public static double SHOOTING_Y = -50;

    // Set upon which target zone to go to based on rings scanned
    private double WOBBLE_X;
    private double WOBBLE_Y;

    public static double QUAD_RING_X = 50;
    public static double QUAD_RING_Y = -50;

    public static double SINGLE_RING_X = 25;
    public static double SINGLE_RING_Y = -15;

    public static double NO_RING_X = -3;
    public static double NO_RING_Y = -50;

    public static double STRAFE_LEFT_TO_RING = 13;
    public static double INTAKE_BACK = 35;

    private String detected;

    private boolean autonRunning = true;

    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);
        ObjectDetection od = new ObjectDetection(hardwareMap);

        Pose2d startPose = new Pose2d(STARTING_X, STARTING_Y, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        waitForStart();

        if (isStopRequested()) return;

        if (opModeIsActive()) {
            while (opModeIsActive() && autonRunning) {
                if (od.tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = od.tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
//                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            od.data(i, recognition, telemetry);
                            i++;
                            if (recognition.getLabel().equals("Quad")) {
                                // QUAD RINGS
                                telemetry.addData("QUAD FOUND", recognition.getConfidence());
                                WOBBLE_X = QUAD_RING_X;
                                WOBBLE_Y = QUAD_RING_Y;
                                detected = "QUAD";
                                break;
                            } else if (recognition.getLabel().equals("Single")) {
                                // SINGLE RING
                                telemetry.addData("SINGLE FOUND", recognition.getConfidence());
                                WOBBLE_X = SINGLE_RING_X;
                                WOBBLE_Y = SINGLE_RING_Y;
                                detected = "SINGLE";
                                break;
                            } else {
                                // NO RINGS
                                telemetry.addLine("NONE FOUND");
                                WOBBLE_X = NO_RING_X;
                                WOBBLE_Y = NO_RING_Y;
                                detected = "NONE";
                                break;
                            }

                        }
                        telemetry.update();


                        Trajectory dropWobbleGoal = drive.trajectoryBuilder(startPose)
                                .splineToConstantHeading(new Vector2d(-25, 0), Math.toRadians(0))
                                .splineToConstantHeading(new Vector2d(WOBBLE_X, WOBBLE_Y), Math.toRadians(0))
                                .addDisplacementMarker(() -> {
                                    mech.setShooter(Mechanisms.motorPower.HIGH);
                                })
                                .build();

                        Trajectory goToShootOne = drive.trajectoryBuilder(dropWobbleGoal.end())
                                .splineToConstantHeading(new Vector2d(SHOOTING_X, SHOOTING_Y), Math.toRadians(0))
                                .build();

                        Trajectory getNewRings = drive.trajectoryBuilder(goToShootOne.end())
                                .strafeLeft(STRAFE_LEFT_TO_RING)
                                .addDisplacementMarker(() -> {
                                    mech.setShooter(Mechanisms.motorPower.STALL);
                                    mech.runIntake(Mechanisms.intakeState.IN);
                                })
                                .build();
//
                        Trajectory goBackIntake = drive.trajectoryBuilder(getNewRings.end())
                                .back(INTAKE_BACK)
                                .build();

                        Trajectory goToShootTwo = drive.trajectoryBuilder(goBackIntake.end())
                                .addDisplacementMarker(() -> {
                                    mech.runIntake(Mechanisms.intakeState.OFF);
                                    mech.setShooter(Mechanisms.motorPower.HIGH);
                                })
                                .lineTo(new Vector2d(SHOOTING_X, SHOOTING_Y))
                                .build();

                        Trajectory parkOnLine = drive.trajectoryBuilder(goToShootTwo.end())
                                .addDisplacementMarker(() -> {
                                    mech.setShooter(Mechanisms.motorPower.HIGH);
                                })
                                .lineTo(new Vector2d(ENDING_X, ENDING_Y))
                                .build();

                        // Follow trajectories in order, may need to place functions
                        // between these instead of using markers
                        drive.followTrajectory(dropWobbleGoal);

                        mech.wobbleArmControl(Mechanisms.wobbleArmPos.AVG);

                        mech.wait(1000);

                        mech.wobbleControl(Mechanisms.wobblePos.OPEN);

                        drive.followTrajectory(goToShootOne);

                        mech.pushRings();

                        if (detected.equals("SINGLE") || detected.equals("QUAD")) {
                            drive.followTrajectory(getNewRings);

                            drive.followTrajectory(goBackIntake);

                            mech.wait(1000);

                            drive.followTrajectory(goToShootTwo);

                            mech.wait(1000);

                            mech.pushRings();

                        }

                        drive.followTrajectory(parkOnLine);

                        autonRunning = false;
                    }
                }
            }
        }

        od.shutdown();
    }
}