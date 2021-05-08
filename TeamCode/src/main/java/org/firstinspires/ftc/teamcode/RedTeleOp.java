package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.nextcore.NextTeleOp;
import org.firstinspires.ftc.teamcode.nextcore.Positions;

@TeleOp(name = "Red TeleOp")
public class RedTeleOp extends NextTeleOp {

    private static Positions pos = new Positions();

    public RedTeleOp() {
        super(pos.RED_ENDING_X,
                pos.RED_ENDING_Y,
                pos.RED_SHOOTING_X,
                pos.RED_SHOOTING_Y);
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
