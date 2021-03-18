package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@TeleOp(name = "Intake Test")
public class IntakeTest extends LinearOpMode {

    public DcMotorEx intake;

    // Init Objects: CRServo
    private CRServo bottomRoller;

    public static double INTAKE_SPEED = 0.7;

    public static double BOTTOM_SPEED = 0.5;

    @Override
    public void runOpMode() {
        intake = hardwareMap.get(DcMotorEx.class, "intake");

        //  Hardware mapping: CRServo
        bottomRoller = hardwareMap.get(CRServo.class, "bottomRoller");

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                intake.setPower(INTAKE_SPEED);
                bottomRoller.setPower(BOTTOM_SPEED);
            }

            if (gamepad1.b) {
                intake.setPower(0);
                bottomRoller.setPower(0);
            }

            if (gamepad1.x) {
                intake.setPower(-INTAKE_SPEED);
                bottomRoller.setPower(-BOTTOM_SPEED);
            }
        }
    }
}
