package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
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
    private DcMotorEx shooterOne, shooterTwo;

    public DcMotorEx intake;

    // Init Objects: CRServo
    private CRServo bottomRoller;

    // Init Objects: Servo
    public Servo indexPush;

    private Servo wobbleGrab, wobbleArm;

    // Init Lists
    private List<DcMotorEx> shooters;

    private List<DcMotorEx> intakes;

    // Other Variables
    public static int PUSH_RESTORE_TIME = 250;

    // Servo Positions
    public static double PUSH_MAX_VALUE = 0;
    public static double PUSH_MIN_VALUE = 0.2;

    public static double WOBBLE_MAX_VALUE = 0.1;
    public static double WOBBLE_MIN_VALUE = 0.8;

    // Power Enum
    public enum motorPower {
        HIGH, STALL, OFF
    }

    // Wobble Enum
    public enum wobbleClawPos {
        OPEN, CLOSE
    }

    // Power Values
    public static double HIGH_POWER = 0.9;
    public static double STALL_POWER = 0.5;

    public static double BOTTOM_ROLLER_POWER = 1;
    public static double INTAKE_POWER = 1;

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
        shooters = Arrays.asList(shooterOne, shooterTwo);

//        shooterOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        shooterTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Init inital Positions
        indexPush.setPosition(PUSH_MIN_VALUE);
        wobbleControl(wobbleClawPos.CLOSE);
    }

    // Set Shooter List to HIGH, STALL, OR OFF
    public void setShooter(motorPower power) {
        switch (power) {
            case HIGH:
                setPowers(shooters, HIGH_POWER);
                break;
            case STALL:
                setPowers(shooters, STALL_POWER);
                break;
            default:
                setPowers(shooters, OFF_POWER);
        }
    }

    // Parent of setShooter that's applicable to any list of motors, accepts double
    private void setPowers(List<DcMotorEx> motors, double power) {
        for (DcMotorEx motor : motors) {
            motor.setPower(power);
        }
    }

//    private void setPositions(List<Servo> servos, double position) {
//        for (Servo servo : servos) {
//            servo.setPosition(position);
//        }
//    }

//    public void toggleThroughShooter(motorPower shooterPower) {
//        switch (shooterPower) {
//            case OFF:
//                shooterPower = Mechanisms.motorPower.HIGH;
//            case HIGH:
//                shooterPower = Mechanisms.motorPower.LOW;
//            case LOW:
//                shooterPower = Mechanisms.motorPower.OFF;
//        }
//    }

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
            case STALL:
                intake.setPower((INTAKE_POWER + OFF_POWER) / 2);
                bottomRoller.setPower((BOTTOM_ROLLER_POWER + OFF_POWER) / 2);
                break;
            default:
                intake.setPower(OFF_POWER);
                bottomRoller.setPower(OFF_POWER);
        }
    }

//    public void moveIntake() {
//        if (intakeLeft.getPosition() == INTAKE_MAX_VALUE) {
//            intakeLeft.setPosition(INTAKE_MIN_VALUE);
//            intakeRight.setPosition(-INTAKE_MIN_VALUE);
//        } else {
//            intakeLeft.setPosition(INTAKE_MAX_VALUE);
//            intakeRight.setPosition(INTAKE_MAX_VALUE);
//        }
//    }

//    public void intakeControl(double intakePower, Gamepad gamepad) {
//        if (gamepad.right_trigger == 1 && gamepad.left_trigger == 1) {
//            if (intakePower == 0) {
//                intakePower = gamepad.right_trigger - gamepad.left_trigger;
//            } else {
//                intakePower = 0;
//            }
//        }
//        intake.setPower(intakePower);
//    }

    // Wait function that doesn't interrupt program runtime, uses elapsed time
    private void wait(int milliseconds) {
        double currTime = runtime.milliseconds();
        double waitUntil = currTime + milliseconds;
        while (runtime.milliseconds() < waitUntil) {
            // remain empty
        }
    }
}
