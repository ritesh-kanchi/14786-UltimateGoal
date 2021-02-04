package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Config
@TeleOp(name = "RingDetector")
public class RingDetector extends LinearOpMode {
    ObjectDetection objD = new ObjectDetection(this);

    @Override
    public void runOpMode() {
        objD.init();
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
                            objD.data(i, recognition);
                            i++;
                            if (recognition.getLabel().equals(objD.LABEL_FIRST_ELEMENT)) {
                                // QUAD RINGS
                            } else if (recognition.getLabel().equals(objD.LABEL_SECOND_ELEMENT)) {
                                // SINGLE RING
                            } else {
                                // NO RINGS
                            }
                        }
                        telemetry.update();
                    }
                }
            }
        }

        objD.shutdown();
    }
}