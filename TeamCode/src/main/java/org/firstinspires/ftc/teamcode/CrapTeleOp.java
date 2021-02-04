package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@Disabled
@TeleOp(name = "CrapTeleOp")
public class CrapTeleOp extends LinearOpMode {

    public DcMotor frontLeftDrive, frontRightDrive, backLeftDrive, backRightDrive, intakeMotor, leftShooterMotor, rightShooterMotor;

    @Override
    public void runOpMode() {
        //drivetrain
        frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left_drive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back_left_drive");
        backRightDrive = hardwareMap.get(DcMotor.class, "back_right_drive");

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        //intake
        intakeMotor = hardwareMap.get(DcMotor.class, "intake_motor");
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);

        //shooter
        leftShooterMotor = hardwareMap.get(DcMotor.class, "left_shooter_motor");
        rightShooterMotor = hardwareMap.get(DcMotor.class, "right_shooter_motor");
        leftShooterMotor.setDirection(DcMotor.Direction.FORWARD);
        rightShooterMotor.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            double forward = -gamepad1.left_stick_y;
            double turn = gamepad1.left_stick_x;
            double strafe = gamepad1.right_stick_x;


            double intakePower = Range.clip(gamepad1.left_trigger, -0.35, 0.35);
            double shooterPower = Range.clip(gamepad1.right_trigger, -1, 1);


//            double frontLeftPower = (y+x+rx);
//            double frontRightPower = (y-x+rx);
//            double backLeftPower = (y-x-rx);
//            double backRightPower = (y+x-rx);
//
//            //motors
//            if ((Math.abs(frontLeftPower) > 1) || (Math.abs(backLeftPower) > 1) || (Math.abs(frontRightPower) > 1) || (Math.abs(backRightPower) >1)) {
//                double max = 0;
//                max = Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower));
//                max = Math.max(Math.abs(frontRightPower), max);
//                max = Math.max(Math.abs(backRightPower), max);
//
//                frontLeftPower /= max;
//                backLeftPower /= max;
//                frontRightPower /= max;
//                backRightPower /= max;
//
//            }
//            frontLeftDrive.setPower(frontLeftPower);
//            frontRightDrive.setPower(frontRightPower);
//            backLeftDrive.setPower(backLeftPower);
//            backRightDrive.setPower(backRightPower);

            frontLeftDrive.setPower(Range.clip(forward + turn + strafe, -0.5, 0.5));
            frontRightDrive.setPower(Range.clip(forward - turn - strafe, -0.5, 0.5));
            backLeftDrive.setPower(Range.clip(forward + turn - strafe, -0.5, 0.5));
            backRightDrive.setPower(Range.clip(forward - turn + strafe, -0.5, 0.5));

            intakeMotor.setPower(intakePower);

            leftShooterMotor.setPower(shooterPower);
            rightShooterMotor.setPower(shooterPower);

        }

    }
}
