package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;
import org.firstinspires.ftc.teamcode.nextcore.ObjectDetection;

import java.util.List;

@Config
@Disabled
@TeleOp(name = "Vanilla Autonomous")
public class VanillaAuton extends LinearOpMode {


    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);
        ObjectDetection objD = new ObjectDetection(hardwareMap);

        Pose2d startPose = new Pose2d(-48, -50, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();
        if (isStopRequested()) return;

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (objD.tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = objD.tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            objD.data(i, recognition, telemetry);
                            i++;
                            if (recognition.getLabel().equals("Quad")) {
                                // QUAD RINGS
                                // SET TRAJECTORY 1, REST ARE ALL THE SAME
                                telemetry.addData("QUAD FOUND",recognition.getConfidence());

                                break;
                            } else if (recognition.getLabel().equals("Single")) {
                                // SINGLE RING
                                // SET TRAJECTORY 1, REST ARE ALL THE SAME
                                telemetry.addData("SINGLE FOUND",recognition.getConfidence());

                                break;
                            } else {
                                // NO RINGS
                                // SET TRAJECTORY 1, REST ARE ALL THE SAME
                                telemetry.addData("NONE FOUND",updatedRecognitions.size());
                                break;
                            }
                        }
                        // drive trajectories go here, shared between all paths, only difference
                        // is the first few path steps
                        telemetry.update();
                    }
                }
            }
        }

        objD.shutdown();
    }
}