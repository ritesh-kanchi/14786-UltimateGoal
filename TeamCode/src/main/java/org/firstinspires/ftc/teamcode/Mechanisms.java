package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class Mechanisms {

    public ElapsedTime runtime = new ElapsedTime();


    private DcMotorEx shooterOne, shooterTwo;

    private List<DcMotorEx> shooters;

    public DcMotorEx intake;

    private Servo indexPush;

    private Servo wobbleGrab;

    private Servo intakeLeft, intakeRight;


    // Servo Positions
    public static double PUSH_MAX_VALUE = 0.1;
    public static double PUSH_MIN_VALUE = 0.8;

    public static double INTAKE_MAX_VALUE = 0.1;
    public static double INTAKE_MIN_VALUE = 0.8;

    public static double WOBBLE_MAX_VALUE = 0.1;
    public static double WOBBLE_MIN_VALUE = 0.8;

    // Speed Levels for Shooter / Read note below
    public enum motorPower {
        HIGH, LOW, OFF
    }

    // Note to Ritesh; Rephrase to differentiate between high Goal and Power Shots?
    private final double HIGH_POWER = 1;
    private final double LOW_POWER = 0.5;
    private final double OFF_POWER = 0;

    public Mechanisms(HardwareMap hardwareMap) {
        shooterOne = hardwareMap.get(DcMotorEx.class, "shooter_one");
        shooterTwo = hardwareMap.get(DcMotorEx.class, "shooter_two");

        intake = hardwareMap.get(DcMotorEx.class, "intake_motor");

        indexPush = hardwareMap.get(Servo.class, "index_push");

        wobbleGrab = hardwareMap.get(Servo.class,"wobble_grab");

        intakeLeft = hardwareMap.get(Servo.class, "intake_left");
        intakeRight = hardwareMap.get(Servo.class, "intake_right");

        shooters = Arrays.asList(shooterOne,shooterTwo);
    }

    public void setShooter(motorPower power) {
       switch (power) {
           case HIGH: setPowers(shooters,HIGH_POWER);
           case LOW: setPowers(shooters,LOW_POWER);
           case OFF: setPowers(shooters,OFF_POWER);
       }
    }

    private void setPowers(List<DcMotorEx> motors, double power) {
        for(DcMotorEx motor : motors) {
            motor.setPower(power);
        }
    }

    private void setPositions(List<Servo> servos, double position) {
        for(Servo servo : servos) {
            servo.setPosition(position);
        }
    }

    public void toggleThroughShooter(motorPower shooterPower) {
        switch (shooterPower) {
            case OFF:
                shooterPower = Mechanisms.motorPower.HIGH;
            case HIGH:
                shooterPower = Mechanisms.motorPower.LOW;
            case LOW:
                shooterPower = Mechanisms.motorPower.OFF;
        }
    }

    public void pushRing() {
            indexPush.setPosition(PUSH_MIN_VALUE);
            wait(500);
            indexPush.setPosition(PUSH_MAX_VALUE);
    }

    public void moveIntake() {
        if(intakeLeft.getPosition() == INTAKE_MAX_VALUE) {
            intakeLeft.setPosition(INTAKE_MIN_VALUE);
            intakeRight.setPosition(-INTAKE_MIN_VALUE);
        } else {
            intakeLeft.setPosition(INTAKE_MAX_VALUE);
            intakeRight.setPosition(INTAKE_MAX_VALUE);
        }
    }

    public void intakeControl(double intakePower, Gamepad gamepad) {
        if (gamepad.right_trigger == 1 && gamepad.left_trigger == 1) {
            if (intakePower == 0) {
                intakePower = gamepad.right_trigger - gamepad.left_trigger;
            } else {
                intakePower = 0;
            }
        }
        intake.setPower(intakePower);
    }

    private void wait(int milliseconds){
        double currTime = runtime.time();
        double waitUntil = currTime + (double)(milliseconds/1000);
        while (runtime.time() < waitUntil){ }
    }
}
