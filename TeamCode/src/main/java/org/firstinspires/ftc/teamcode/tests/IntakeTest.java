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

    @Override
    public void runOpMode() {
        intake = hardwareMap.get(DcMotorEx.class, "intake");

        //  Hardware mapping: CRServo
        bottomRoller = hardwareMap.get(CRServo.class, "bottomRoller");

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                intake.setPower(1);
                bottomRoller.setPower(1);
            }

            if (gamepad1.b) {
                intake.setPower(0);
                bottomRoller.setPower(0);
            }

            if (gamepad1.x) {
                intake.setPower(-1);
                bottomRoller.setPower(-1);
            }
        }
    }
}
