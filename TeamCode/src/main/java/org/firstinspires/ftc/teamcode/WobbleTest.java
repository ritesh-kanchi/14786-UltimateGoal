package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name = "Wobble Test")
public class WobbleTest extends LinearOpMode {

    private Servo wobbleGrab, wobbleArm;

    public static double WOBBLE_MAX_VALUE = 0.1;
    public static double WOBBLE_MIN_VALUE = 0.8;

    @Override
    public void runOpMode() {
        wobbleGrab = hardwareMap.get(Servo.class, "wobbleGrab");
        wobbleArm = hardwareMap.get(Servo.class, "wobbleArm");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) wobbleGrab.setPosition(WOBBLE_MIN_VALUE);

            if (gamepad1.b) wobbleGrab.setPosition(WOBBLE_MAX_VALUE);

            wobbleArm.setPosition(gamepad1.left_trigger);
        }
    }
}
