package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@Config
//@Disabled
@TeleOp(name = "ServoTest")
public class ServoTest extends LinearOpMode {

    public static double STARTING_POSITION = 0.2;
    public static double ENDING_POSITION = 0;

    private DcMotorEx shooterOne, shooterTwo;

    public static double MOTOR_POWER = 1;


    Servo pushServo,clawPosition;

    @Override
    public void runOpMode() {

        pushServo = hardwareMap.get(Servo.class, "indexPush");
//        clawServo = hardwareMap.get(Servo.class,"clawServo");

        shooterOne = hardwareMap.get(DcMotorEx.class, "shooterOne");
        shooterTwo = hardwareMap.get(DcMotorEx.class, "shooterTwo");

//        intake = hardwareMap.get(DcMotorEx.class,"intakeMotor");

        shooterOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        shooterTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        pushServo.setPosition(STARTING_POSITION);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                pushServo.setPosition(STARTING_POSITION);
            }

            if (gamepad1.b) {
                pushServo.setPosition(ENDING_POSITION);
            }

            if (gamepad1.x) {
                shooterOne.setPower(MOTOR_POWER);
                shooterTwo.setPower(MOTOR_POWER);
            }

            if (gamepad1.y) {
                shooterOne.setPower(0);
                shooterTwo.setPower(0);
            }

//            if(gamepad1.dpad_left) clawServo.setPosition(0);
//
//            if(gamepad1.dpad_right) clawServo.setPosition(0.8);


        }
    }
}
