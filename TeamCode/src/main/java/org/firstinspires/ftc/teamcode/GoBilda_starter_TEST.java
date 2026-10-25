//package org.firstinspires.ftc.teamcode;
//
//import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.PIDFCoefficients;
//
///*
// * BIOBUZZ TeleOp for the goBILDA StarterBot (mecanum version).
// */
//@TeleOp(name = "BIOBUZZ TeleOp - StarterBot", group = "BioBuzz")
//public class GoBilda_starter_TEST extends OpMode {
//
//    // Drivetrain
//    private DcMotor leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive;
//    double leftFrontPower, rightFrontPower, leftBackPower, rightBackPower;
//
//    // Intake
//    private DcMotor intake;
//    private CRServo leftIntakeServo, rightIntakeServo;
//    double intakePower;
//
//    // Launcher
//    private DcMotorEx launcher;
//    private CRServo windmillServo;
//
//    public final int LAUNCHER_TARGET_VELOCITY = 1250;
//    public final int LAUNCHER_MIN_VELOCITY = 1200;
//
//    @Override
//    public void init() {
//
//        // Must match the hardware config names on the Driver Station.
//        leftFrontDrive = hardwareMap.get(DcMotor.class, "lf");
//        rightFrontDrive = hardwareMap.get(DcMotor.class, "rf");
//        leftBackDrive = hardwareMap.get(DcMotor.class, "lb");
//        rightBackDrive = hardwareMap.get(DcMotor.class, "rb");
//        intake = hardwareMap.get(DcMotor.class, "intake");
//        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
//        windmillServo = hardwareMap.get(CRServo.class, "windmillServo");
//        leftIntakeServo = hardwareMap.get(CRServo.class, "left_intake_servo");
//        rightIntakeServo = hardwareMap.get(CRServo.class, "right_intake_servo");
//
//        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
//        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
//        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
//        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
//
//        leftFrontDrive.setZeroPowerBehavior(BRAKE);
//        rightFrontDrive.setZeroPowerBehavior(BRAKE);
//        leftBackDrive.setZeroPowerBehavior(BRAKE);
//        rightBackDrive.setZeroPowerBehavior(BRAKE);
//        intake.setZeroPowerBehavior(BRAKE);
//
//        // Launcher uses velocity control, not raw power.
//        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5));
//
//        leftIntakeServo.setPower(0);
//        rightIntakeServo.setPower(0);
//        windmillServo.setPower(0);
//
//        rightIntakeServo.setDirection(DcMotorSimple.Direction.REVERSE);
//        windmillServo.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        telemetry.addData("Status", "Initialized");
//    }
//
//    @Override
//    public void loop() {
//
//        mecanumDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
//
//        if (gamepad1.rightBumperWasPressed()) {
//            intakePower = 1;
//        } else if (gamepad1.leftBumperWasPressed()) {
//            intakePower = -1;
//        } else {
//            intakePower = 0;
//        }
//
//        if (gamepad1.right_trigger_pressed) {
//            launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
//        }
//        if (gamepad1.left_trigger_pressed) {
//            launcher.setVelocity(0);
//        }
//
//        intake.setPower(intakePower);
//        leftIntakeServo.setPower(intakePower);
//        rightIntakeServo.setPower(intakePower);
//
//        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftFrontPower, rightFrontPower);
//        telemetry.addData("Launcher velocity", "%.0f / %d", launcher.getVelocity(), LAUNCHER_TARGET_VELOCITY);
//    }
//
//    void mecanumDrive(double forward, double strafe, double rotate) {
//        leftFrontPower = forward + strafe + rotate;
//        rightFrontPower = forward - strafe - rotate;
//        leftBackPower = forward - strafe + rotate;
//        rightBackPower = forward + strafe - rotate;
//
//        double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
//        max = Math.max(max, Math.abs(leftBackPower));
//        max = Math.max(max, Math.abs(rightBackPower));
//
//        if (max > 1.0) {
//            leftFrontPower /= max;
//            rightFrontPower /= max;
//            leftBackPower /= max;
//            rightBackPower /= max;
//        }
//
//        leftFrontDrive.setPower(leftFrontPower);
//        rightFrontDrive.setPower(rightFrontPower);
//        leftBackDrive.setPower(leftBackPower);
//        rightBackDrive.setPower(rightBackPower);
//    }
//
//    void launch() {
//        if (gamepad1.right_bumper) {
//            launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
//        }
//        if (gamepad1.left_bumper) {
//            launcher.setVelocity(0);
//        }
//
//        if (gamepad1.right_bumper && launcher.getVelocity() > LAUNCHER_MIN_VELOCITY) {
//            windmillServo.setPower(1);
//            intakePower += 0.5;
//        } else {
//            windmillServo.setPower(0);
//        }
//    }
//}