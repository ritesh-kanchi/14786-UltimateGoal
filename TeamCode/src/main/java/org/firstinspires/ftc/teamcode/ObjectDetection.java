package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

@Config
public class ObjectDetection {
    // TF Assets and Models
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    private static final String VUFORIA_KEY = "AfjMuRf/////AAABmYxyagolxE9planYvv3uxyxYZtX1eFm+gKoobiWAGar3X0GYvUqRabO3xrGUIHaARVE+Nkfhw5T3r3LvAAzJ9r4LFhDnEtk1yFl5M+nSrh+LOSFuEyce17bZ4QSV5TL6Aewbg47wSVRNaoZAVBtNOrOnfE+5RWjUyDB0KP0TdtOQxSLLbiGl13k+w/A4qc73H11TGSNEcrRWTwzy/2YhfCt4wmnvB54z2+c31vtkO6HIeUCOvdO91M8F5wZ6Y154r0Z2L/5nJYL9jN2dl8tU6kWd37LNiiVBgcPyaPpuLNPAs8aBVjzjdR7zd7UZNLV2yB1bTBfzVlYfivalbGCwxkPpjBccZASMMuw1vInDmy/L";

    // Init Objects
    private VuforiaLocalizer vuforia;

    public TFObjectDetector tfod;

    private LinearOpMode opmode;

    // Variables
    public static float CON_VALUE = 0.8f;

    public static double MAGNIFICATION_VALUE = 1.5;

    public static double ASPECT_RATIO_W = 4.0;

    public static double ASPECT_RATIO_H = 3.0;

    public enum OBJECT {
        NONE, SINGLE, QUAD
    }

    // Constructor
    public ObjectDetection(LinearOpMode opMode) {
        opmode = opMode;

        // Init Object Detection Technologies
        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(MAGNIFICATION_VALUE, ASPECT_RATIO_W / ASPECT_RATIO_H);


        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = opmode.hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        FtcDashboard.getInstance().startCameraStream(vuforia, 0);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.

//        Vuforia.setFrameFormat(PIXEL_FORMAT.GRAYSCALE, true);
        vuforia.setFrameQueueCapacity(1);
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = opmode.hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", opmode.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = CON_VALUE;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    // Get Data of each recognition
    public void data(int i, Recognition recognition) {
        opmode.telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
        opmode.telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                recognition.getLeft(), recognition.getTop());
        opmode.telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                recognition.getRight(), recognition.getBottom());
    }

    // Shutdown to turn off TF
    public void shutdown() {
        if (tfod != null) {
            tfod.shutdown();
        }
    }
}
