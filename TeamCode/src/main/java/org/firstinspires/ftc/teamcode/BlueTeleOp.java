package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.nextcore.NextTeleOp;
import org.firstinspires.ftc.teamcode.nextcore.Positions;

@TeleOp(name = "Blue TeleOp")
public class BlueTeleOp extends NextTeleOp {

    private static Positions pos = new Positions();

    public BlueTeleOp() {
        super(pos.BLUE_ENDING_X,
                pos.BLUE_ENDING_Y,
                pos.BLUE_SHOOTING_X,
                pos.BLUE_SHOOTING_Y);
    }

    @Override
    public void runOpMode() {
        super.runOpMode();
        while(opModeIsActive()) {
            super.runDrive();
            super.runControls();
        }
    }
}
