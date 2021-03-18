package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@TeleOp(name = "Velocity Shooter")
public class VelShooter extends LinearOpMode {

    public ElapsedTime runtime = new ElapsedTime();

    private DcMotorEx shooterOne, shooterTwo;

    public Servo indexPush;

    public static double SHOOT_TPS = 2700;

    private double currentTPS = 0;

    // Servo Positions
    public static double PUSH_MAX_VALUE = 0;
    public static double PUSH_MIN_VALUE = 0.2;

    // Other Variables
    public static int PUSH_RESTORE_TIME = 250;

    @Override
    public void runOpMode() {
        shooterOne = hardwareMap.get(DcMotorEx.class, "shooterOne");
        shooterTwo = hardwareMap.get(DcMotorEx.class, "shooterTwo");

        indexPush = hardwareMap.get(Servo.class, "indexPush");

        shooterOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooterTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        shooterOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        indexPush.setPosition(PUSH_MIN_VALUE);

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                currentTPS = SHOOT_TPS;
            }

            if (gamepad1.b) {
                currentTPS = 0;
            }

            if (gamepad1.x) {
                indexPush.setPosition(PUSH_MAX_VALUE);
                wait(PUSH_RESTORE_TIME);
                indexPush.setPosition(PUSH_MIN_VALUE);
                wait(PUSH_RESTORE_TIME);

                indexPush.setPosition(PUSH_MAX_VALUE);
                wait(PUSH_RESTORE_TIME);
                indexPush.setPosition(PUSH_MIN_VALUE);
                wait(PUSH_RESTORE_TIME);

                indexPush.setPosition(PUSH_MAX_VALUE);
                wait(PUSH_RESTORE_TIME);
                indexPush.setPosition(PUSH_MIN_VALUE);
                wait(PUSH_RESTORE_TIME);

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

    private void wait(int milliseconds) {
        double currTime = runtime.milliseconds();
        double waitUntil = currTime + milliseconds;
        while (runtime.milliseconds() < waitUntil) {
            // remain empty
        }
    }

}
