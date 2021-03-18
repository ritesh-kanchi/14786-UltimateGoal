package org.firstinspires.ftc.teamcode.nextcore;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Arrays;
import java.util.List;

@Config
public class Mechanisms {
    // Init Objects
    public ElapsedTime runtime = new ElapsedTime();

    // Init Objects: DcMotorEx
    public DcMotorEx shooterOne, shooterTwo;

    public DcMotorEx intake;

    // Init Objects: CRServo
    private CRServo bottomRoller;

    // Init Objects: Servo
    public Servo indexPush;

    private Servo wobbleGrab, wobbleArm;

    // Init Lists

    // Other Variables
    public static int PUSH_RESTORE_TIME = 100;

    // Servo Positions
    public static double PUSH_MAX_VALUE = 0;
    public static double PUSH_MIN_VALUE = 0.2;

    public static double WOBBLE_MAX_VALUE = 0.1;
    public static double WOBBLE_MIN_VALUE = 0.8;

    public static double SHOOT_TPS = -900;

    // Power Enum
    public enum motorPower {
        HIGH, STALL, OFF
    }

    // Wobble Enum
    public enum wobbleClawPos {
        OPEN, CLOSE
    }

    // Power Values
    public static double BOTTOM_ROLLER_POWER = 0.5;
    public static double INTAKE_POWER = 0.7;

    private static double OFF_POWER = 0;

    // Constructor
    public Mechanisms(HardwareMap hardwareMap) {
        //  Hardware mapping: DcMotorEx
        shooterOne = hardwareMap.get(DcMotorEx.class, "shooterOne");
        shooterTwo = hardwareMap.get(DcMotorEx.class, "shooterTwo");

        intake = hardwareMap.get(DcMotorEx.class, "intake");

        //  Hardware mapping: CRServo
        bottomRoller = hardwareMap.get(CRServo.class, "bottomRoller");

        //  Hardware mapping: Servo
        indexPush = hardwareMap.get(Servo.class, "indexPush");

        wobbleGrab = hardwareMap.get(Servo.class, "wobbleGrab");
        wobbleArm = hardwareMap.get(Servo.class, "wobbleArm");

        // Set lists

        intake.setDirection(DcMotor.Direction.REVERSE);
        bottomRoller.setDirection(DcMotor.Direction.REVERSE);

        shooterOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooterTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        shooterOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Init inital Positions
        indexPush.setPosition(PUSH_MIN_VALUE);
        wobbleControl(wobbleClawPos.CLOSE);
    }

    // Set Shooter List to HIGH, STALL, OR OFF
    public void setShooter(motorPower power) {
        switch (power) {
            case HIGH:
                shooterOne.setVelocity(SHOOT_TPS);
                shooterTwo.setVelocity(SHOOT_TPS);
                break;
            case STALL:
                shooterOne.setVelocity(SHOOT_TPS / 2);
                shooterTwo.setVelocity(SHOOT_TPS / 2);
                break;
            default:
                shooterOne.setVelocity(OFF_POWER);
                shooterTwo.setVelocity(OFF_POWER);
        }
    }


    // Hits rings three times into shooter
    public void pushRings() {

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

    public void wobbleControl(wobbleClawPos pos) {
        switch (pos) {
            case OPEN:
                wobbleGrab.setPosition(WOBBLE_MAX_VALUE);
                break;
            case CLOSE:
                wobbleGrab.setPosition(WOBBLE_MIN_VALUE);
                break;
        }
    }

    public void runIntake(motorPower power) {
        switch (power) {
            case HIGH:
                intake.setPower(INTAKE_POWER);
                bottomRoller.setPower(BOTTOM_ROLLER_POWER);
                break;
            default:
                intake.setPower(OFF_POWER);
                bottomRoller.setPower(OFF_POWER);
        }
    }


    // Wait function that doesn't interrupt program runtime, uses elapsed time
    private void wait(int milliseconds) {
        double currTime = runtime.milliseconds();
        double waitUntil = currTime + milliseconds;
        while (runtime.milliseconds() < waitUntil) {
            // remain empty
        }
    }
}
