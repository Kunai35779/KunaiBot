
package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "DT", group = "BioBuzz")
public class DT extends OpMode {
    // Drivetrain
    private DcMotor fl, bl, fr, br;
    // private IMU imu;
    private final double maxSpeed = 1.0;

    private static final double FRONT_OFFSET = Math.PI;

    // Intake
    private DcMotor intake;
    private CRServo ls, rs;

    private CRServo transfer;

    private DcMotor shooter;

    @Override
    public void init() {
        // DT:
        fl = hardwareMap.get(DcMotor.class, "fl");
        bl = hardwareMap.get(DcMotor.class, "bl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        br = hardwareMap.get(DcMotor.class, "br");

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        fl.setZeroPowerBehavior(BRAKE);
        bl.setZeroPowerBehavior(BRAKE);
        fr.setZeroPowerBehavior(BRAKE);
        br.setZeroPowerBehavior(BRAKE);

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Int
        intake = hardwareMap.get(DcMotor.class, "intake");
        ls = hardwareMap.get(CRServo.class, "ls");
        rs = hardwareMap.get(CRServo.class, "rs");

        ls.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");

        //Transfer
        transfer = hardwareMap.get(CRServo.class, "transfer");

        telemetry.addData("Initialized", "Transfer");

        //Shooter
        shooter = hardwareMap.get(DcMotor.class,"shooter");

        shooter.setDirection(DcMotorSimple.Direction.FORWARD);

        telemetry.addData("Shooter", "Initialized");
    }

    @Override
    public void loop() {

        // FC
        double y = -gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x;
        double rx = -gamepad1.right_stick_x;


        // New
        double flPower = y + x + rx;
        double blPower = y - x + rx;
        double frPower = y - x - rx;
        double brPower = y + x - rx;

        double maxPower = 1.0;
        maxPower = Math.max(maxPower, Math.abs(flPower));
        maxPower = Math.max(maxPower, Math.abs(blPower));
        maxPower = Math.max(maxPower, Math.abs(frPower));
        maxPower = Math.max(maxPower, Math.abs(brPower));

        fl.setPower(maxSpeed * (flPower / maxPower));
        bl.setPower(maxSpeed * (blPower / maxPower));
        fr.setPower(maxSpeed * (frPower / maxPower));
        br.setPower(maxSpeed * (brPower / maxPower));

        // Int
        double intakePower = 1.0;



        intake.setPower(intakePower);
        ls.setPower(intakePower);
        rs.setPower(intakePower);

        //Transfer
        double transfer_power = gamepad1.left_trigger;

        transfer.setPower(transfer_power);

        //Shooter
        double shooter_power = 0.65;

        shooter.setPower(shooter_power);


    }


}
