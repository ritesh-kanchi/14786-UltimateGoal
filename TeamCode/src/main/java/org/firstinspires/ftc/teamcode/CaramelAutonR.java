package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import java.util.List;

@Config
@Autonomous(name = "CaramelAutonR")
public class CaramelAutonR extends LinearOpMode {

    private ObjectDetection.OBJECT detected;

    // Position Coordinates
    private static double STARTING_X = -63;
    private static double STARTING_Y = -25;

    private static double SHOOTING_X = 0;
    private static double SHOOTING_Y = -35;

    // Set upon which target zone to go to based on rings scanned
    private double WOBBLE_X;
    private double WOBBLE_Y;

    private static double QUAD_RING_X = 50;
    private static double QUAD_RING_Y = -50;

    private static double SINGLE_RING_X = 25;
    private static double SINGLE_RING_Y = -25;

    private static double NO_RING_X = 0;
    private static double NO_RING_Y = -50;

    private static double LINE_X = 10;
    private static double LINE_Y = -35;

    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);
        ObjectDetection od = new ObjectDetection(this);

        Pose2d startPose = new Pose2d(STARTING_X, STARTING_Y, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        waitForStart();

        if (isStopRequested()) return;

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (od.tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = od.tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            od.data(i, recognition);
                            i++;
                            if (recognition.getLabel().equals("Quad")) {
                                // QUAD RINGS
                                telemetry.addData("QUAD FOUND", recognition.getConfidence());
                                detected = ObjectDetection.OBJECT.QUAD;
                                WOBBLE_X = QUAD_RING_X;
                                WOBBLE_Y = QUAD_RING_Y;

                                break;
                            } else if (recognition.getLabel().equals("Single")) {
                                // SINGLE RING
                                telemetry.addData("SINGLE FOUND", recognition.getConfidence());
                                detected = ObjectDetection.OBJECT.QUAD;
                                WOBBLE_X = SINGLE_RING_X;
                                WOBBLE_Y = SINGLE_RING_Y;

                                break;
                            } else {
                                // NO RINGS
                                telemetry.addData("NONE FOUND", updatedRecognitions.size());
                                detected = ObjectDetection.OBJECT.NONE;
                                WOBBLE_X = NO_RING_X;
                                WOBBLE_Y = NO_RING_Y;

                                break;
                            }

                        }
                        telemetry.update();

                        Trajectory goToShootOne = drive.trajectoryBuilder(startPose)
                                .addDisplacementMarker(() -> {
                                    mech.setShooter(Mechanisms.motorPower.STALL);
                                })
                                .splineToConstantHeading(new Vector2d(-25, 0), Math.toRadians(0))
                                .splineToConstantHeading(new Vector2d(SHOOTING_X, SHOOTING_Y), Math.toRadians(0))
                                .addDisplacementMarker(() -> {
                                    mech.setShooter(Mechanisms.motorPower.HIGH);
                                    mech.pushRings();
                                    mech.setShooter(Mechanisms.motorPower.OFF);
                                })
                                .build();

                        Trajectory placeWobble = drive.trajectoryBuilder(goToShootOne.end())
                                .splineToConstantHeading(new Vector2d(WOBBLE_X, WOBBLE_Y), Math.toRadians(0))
                                .addDisplacementMarker(() -> {
                                    mech.wobbleControl(Mechanisms.wobbleClawPos.OPEN);
                                    mech.wobbleControl(Mechanisms.wobbleClawPos.CLOSE);
                                })
                                .build();

                        Trajectory getNewRings = drive.trajectoryBuilder(placeWobble.end())
                                .splineToConstantHeading(new Vector2d(SHOOTING_X, SHOOTING_Y), Math.toRadians(0))
                                .addDisplacementMarker(() -> {
                                    mech.runIntake(Mechanisms.motorPower.HIGH);
                                })
                                .lineTo(new Vector2d(-35, SHOOTING_Y))

                                .build();

                        Trajectory goToShootTwo = drive.trajectoryBuilder(getNewRings.end())
                                .addDisplacementMarker(() -> {
                                    mech.runIntake(Mechanisms.motorPower.OFF);
                                    mech.setShooter(Mechanisms.motorPower.STALL);
                                })
                                .lineTo(new Vector2d(SHOOTING_X, SHOOTING_Y))
                                .addDisplacementMarker(() -> {
                                    mech.setShooter(Mechanisms.motorPower.HIGH);
                                    mech.pushRings();
                                    mech.setShooter(Mechanisms.motorPower.OFF);
                                })
                                .build();

                        Trajectory parkOnLine = drive.trajectoryBuilder(goToShootTwo.end())
                                .lineTo(new Vector2d(LINE_X, LINE_Y))
                                .build();

                        // Follow trajectories in order, may need to place functions
                        // between these instead of using markers
                        drive.followTrajectory(goToShootOne);
                        drive.followTrajectory(placeWobble);
                        drive.followTrajectory(getNewRings);
                        drive.followTrajectory(goToShootTwo);
                        drive.followTrajectory(parkOnLine);
                    }
                }
            }
        }

        od.shutdown();
    }
}