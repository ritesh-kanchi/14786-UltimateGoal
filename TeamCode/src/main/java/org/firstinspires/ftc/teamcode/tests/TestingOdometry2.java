package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.util.Encoder;

@Config
@Disabled
@TeleOp(name = "TestingOdometry2")
public class TestingOdometry2 extends LinearOpMode {

//    Designed for no drive encoders, only dead wheels

    private Encoder leftEncoder, rightEncoder, frontEncoder;

    @Override
    public void runOpMode() {
        leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftBack"));
        rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightFront"));
        frontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftFront"));

        leftEncoder.setDirection(Encoder.Direction.REVERSE);
        rightEncoder.setDirection(Encoder.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Left Encoder", leftEncoder.getCurrentPosition());
            telemetry.addData("Right Encoder", rightEncoder.getCurrentPosition());
            telemetry.addData("Front Encoder", frontEncoder.getCurrentPosition());
            telemetry.update();
        }
    }

}
