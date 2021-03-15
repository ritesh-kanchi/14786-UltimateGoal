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

    private Trajectory placeWobble;

    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);
        ObjectDetection od = new ObjectDetection(this);

        Pose2d startPose = new Pose2d(-63, -25, Math.toRadians(0));

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
                                break;
                            } else if (recognition.getLabel().equals("Single")) {
                                // SINGLE RING
                                telemetry.addData("SINGLE FOUND", recognition.getConfidence());
                                detected = ObjectDetection.OBJECT.QUAD;
                                break;
                            } else {
                                // NO RINGS
                                detected = ObjectDetection.OBJECT.NONE;
                                break;
                            }

                        }
                        telemetry.update();

                        Trajectory goToShootOne = drive.trajectoryBuilder(startPose)
                                // TIME MARKER TO START MOTOR, RUN to 0.9
                                .splineToConstantHeading(new Vector2d(-25, 0), Math.toRadians(0))
                                .splineToConstantHeading(new Vector2d(0, -35), Math.toRadians(0))
                                //ADD DISPLACEMENT MARKER FOR SERVO AND TURN OFF MOTOR/STALL IT, STOP
                                // MOVEMENT PERHAPS? OR WE CAN ADD AS SEPERATE FUNCTION AFTER MOVEMENT
                                .build();

                        // GO TO BOTTOM LEFT CORNER OF EACH TO MAKE THE DROP
                        switch (detected) {
                            case QUAD:
                                placeWobble = drive.trajectoryBuilder(goToShootOne.end())
                                        .splineToConstantHeading(new Vector2d(50, -50), Math.toRadians(0))
                                        .build();
                                break;
                            case SINGLE:
                                placeWobble = drive.trajectoryBuilder(goToShootOne.end())
                                        .splineToConstantHeading(new Vector2d(25, -25), Math.toRadians(0))
                                        .build();
                                break;
                            default:
                                placeWobble = drive.trajectoryBuilder(goToShootOne.end())
                                        .splineToConstantHeading(new Vector2d(0, -50), Math.toRadians(0))
                                        .build();
                                break;
                        }

                        Trajectory getNewRings = drive.trajectoryBuilder(placeWobble.end())
                                .splineToConstantHeading(new Vector2d(-15 - 35), Math.toRadians(0))
                                // START INTAKE
                                .splineToConstantHeading(new Vector2d(-35 - 35), Math.toRadians(0))

                                .build();

                        Trajectory goToShootTwo = drive.trajectoryBuilder(getNewRings.end())
                                // TIME MARKER TO START MOTOR, RUN to 0.9
                                .splineToConstantHeading(new Vector2d(0, -35), Math.toRadians(0))
                                //ADD DISPLACEMENT MARKER FOR SERVO AND TURN OFF MOTOR/STALL IT, STOP
                                // MOVEMENT PERHAPS? OR WE CAN ADD AS SEPERATE FUNCTION AFTER MOVEMENT
                                .build();

                        Trajectory parkOnLine = drive.trajectoryBuilder(goToShootTwo.end())
                                .splineToConstantHeading(new Vector2d(10, -35), Math.toRadians(0))
                                .build();

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