package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.nextcore.NextTeleOp;
import org.firstinspires.ftc.teamcode.nextcore.Positions;

@TeleOp(name = "Red TeleOp")
public class RedTeleOp extends NextTeleOp {

    private static Positions pos = new Positions();

    Vector2d SHOOTING_POSITION = new Vector2d(pos.RED_SHOOTING_X, pos.RED_SHOOTING_Y);

    Vector2d POWERSHOT_1 = new Vector2d(pos.RED_POWERSHOT_X, pos.RED_POWERSHOT_Y_1);
    Vector2d POWERSHOT_2 = new Vector2d(pos.RED_POWERSHOT_X, pos.RED_POWERSHOT_Y_2);
    Vector2d POWERSHOT_3 = new Vector2d(pos.RED_POWERSHOT_X, pos.RED_POWERSHOT_Y_3);


    public RedTeleOp() {
        super(pos.RED_ENDING_X,
                pos.RED_ENDING_Y);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        while (opModeIsActive() && !isStopRequested()) {

            Pose2d poseEstimate = drive.getPoseEstimate();

            super.driveData(poseEstimate);

            super.autoControl(poseEstimate,SHOOTING_POSITION,POWERSHOT_1,POWERSHOT_2,POWERSHOT_3);

        }
    }
}
