package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.nextcore.ObjectDetection;

import java.util.List;

@Config
//@Disabled
@TeleOp(name = "RingDetector")
public class RingDetector extends LinearOpMode {


    private ObjectDetection.OBJECT detected;

    @Override
    public void runOpMode() {
        ObjectDetection objD = new ObjectDetection(hardwareMap);
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();
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
                                telemetry.addData("QUAD FOUND",recognition.getConfidence());
//                                detected = ObjectDetection.OBJECT.QUAD;
                            } else if (recognition.getLabel().equals("Single")) {
                                // SINGLE RING
                                telemetry.addData("SINGLE FOUND", recognition.getConfidence());
//                                detected = ObjectDetection.OBJECT.QUAD;
                            } else {
                                // NO RINGS
                                telemetry.addData("SINGLE FOUND", updatedRecognitions.size());
//                                detected = ObjectDetection.OBJECT.NONE;
                            }

                            if (detected != null) break;

                        }
                        telemetry.update();
                    }
                }
            }
        }

        objD.shutdown();
    }
}