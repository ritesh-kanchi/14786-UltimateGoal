package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name = "Wobble Test")
public class WobbleTest extends LinearOpMode {

    private Servo wobbleGrab, wobbleArm;

    public static double WOBBLE_MAX_VALUE = 0.99;
    public static double WOBBLE_MIN_VALUE = 0.1;

    public static double WOBBLE_CLAW_MIN_VALUE = 0.99;
    public static double WOBBLE_CLAW_MAX_VALUE = 0.1;

    @Override
    public void runOpMode() {
        wobbleGrab = hardwareMap.get(Servo.class, "wobbleGrab");
        wobbleArm = hardwareMap.get(Servo.class, "wobbleArm");

        wobbleArm.setPosition(WOBBLE_MAX_VALUE);

        wobbleGrab.setPosition(WOBBLE_CLAW_MAX_VALUE);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) wobbleGrab.setPosition(WOBBLE_CLAW_MIN_VALUE);

            if (gamepad1.b) wobbleGrab.setPosition(WOBBLE_CLAW_MAX_VALUE);

            if (gamepad1.dpad_up) wobbleArm.setPosition(WOBBLE_MAX_VALUE);

            if (gamepad1.dpad_down) wobbleArm.setPosition(WOBBLE_MIN_VALUE);

            if (gamepad1.dpad_left)
                wobbleArm.setPosition((WOBBLE_MAX_VALUE + WOBBLE_MIN_VALUE) / 2);

            if (gamepad1.dpad_right) wobbleArm.setPosition(0.75);
        }
    }
}
