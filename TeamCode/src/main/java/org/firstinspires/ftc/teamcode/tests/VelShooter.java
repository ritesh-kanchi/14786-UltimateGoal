package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@TeleOp(name = "Velocity Shooter")
public class VelShooter extends LinearOpMode {

    private DcMotorEx shooterOne, shooterTwo;

    public static double SHOOT_TPS = 2700;

    private double currentTPS = 0;

    @Override
    public void runOpMode() {
        shooterOne = hardwareMap.get(DcMotorEx.class, "shooterOne");
        shooterTwo = hardwareMap.get(DcMotorEx.class, "shooterTwo");

        shooterOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooterTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        shooterOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                currentTPS = SHOOT_TPS;
            }

            if (gamepad1.b) {
                currentTPS = 0;
            }

            shooterOne.setVelocity(currentTPS);
            shooterTwo.setVelocity(currentTPS);

            telemetry.addData("S1 Current", shooterOne.getCurrentPosition());
            telemetry.addData("S2 Current", shooterTwo.getCurrentPosition());
            telemetry.addLine();
            telemetry.addData("S Expected", currentTPS);
            telemetry.update();
        }
    }
}
