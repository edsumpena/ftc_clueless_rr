package org.firstinspires.ftc.teamcode.drivetrain_test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Joshua on 9/9/2017.
 */

public class FourWheelMecanumDrivetrain {


    public enum Direction {
        FORWARD,
        RIGHT,
        BACKWARD,
        LEFT
    }
    public RobotHardware rw;

    LinearOpMode runningOpMode;

    Orientation angles;

    double speedMultiplier = 0.75;

    // Constants used to adjust various parameters / characteristics of the drivetrain
    final double rotSpeed = 0.75;
    final double speedThreshold = 0.05;
    public double turnThreshold = 2;

    public FourWheelMecanumDrivetrain(RobotHardware rw) {
        this.rw = rw;
    }

    // region auto
    // Primary movement method for auto

    public void setRunningOpMode(LinearOpMode opMode) {
        this.runningOpMode = opMode;
    }

    public void AutoMove(double speed, double angle, double time) throws InterruptedException{
        MoveAngle(speed, angle, 0);
        Thread.sleep((long)(time * 1000));
        stop();
    }

    public void AutoMove(Direction direction, double speed, double time) throws InterruptedException{
        MoveCardinal(direction, (float)speed);
        Thread.sleep((long)(time * 1000));
        stop();
    }

    // "Dumb" turn, based on time
    public void turn(boolean clockwise, double speed, double seconds) throws InterruptedException{
        Rotate(clockwise, speed);
        Thread.sleep((long)(seconds * 1000));
        stop();
    }

    public void resetEncoders() {
        DcMotor.RunMode runMode = rw.frontLeft.getMode();
        setMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorMode(runMode);
    }
    public boolean anyIsBusy() {
        return rw.backLeft.isBusy() || rw.backRight.isBusy() || rw.frontLeft.isBusy() || rw.frontRight.isBusy();
    }
    public void setPowerAll(double power) {
        rw.backLeft.setPower(power);
        rw.backRight.setPower(power);
        rw.frontLeft.setPower(power);
        rw.frontRight.setPower(power);
    }

    // Gyroscope Sensor based turn, untested
    /*public void GyroTurn(double speed, double angle) {
        // Angle is counterclockwise (sorry)

        double normalizedHeading = normalize(getHeading());
        double normalizedAngle = normalize(angle);
        double angleDiff = normalizedHeading - normalizedAngle;

        angleDiff = (angleDiff / 180) * Math.PI;
        double c = sin(angleDiff);
        if (c >= 0) {
            // CW
            MoveAngle(0, 0, speed);

        }
        else if (c < 0) {
            // CCW
            MoveAngle(0, 0, -speed);
        }

        while (true) {
            double angle1 = normalize(angle + turnThreshold);
            double angle2 = normalize(angle - turnThreshold);
            double target = normalize(getHeading());
            double diff = normalize(angle2 - angle1);
            if (diff > 180) {
                double temp = angle1;
                angle1 = angle2;
                angle2 = temp;
            }
            boolean within = false;
            if (angle1 <= angle2) {
                within = target >= angle1 && target <= angle2;
            }

            else {
                within = target >= angle1 || target <= angle2;
            }

            if (within) {
                stop();
                break;
            }
        }
    }*/

    /*public void GyroTurnTeleop(double speed, double angle) {
        // Angle is counterclockwise (sorry)
        double normalizedHeading = normalize(getHeading());
        double normalizedAngle = normalize(angle);
        double angleDiff = normalizedHeading - normalizedAngle;

        angleDiff = (angleDiff / 180) * Math.PI;
        double c = sin(angleDiff);
        if (c >= 0) {
            // CW
            MoveAngle(0, 0, speed);

        }
        else if (c < 0) {
            // CCW
            MoveAngle(0, 0, -speed);
        }
    }*/



    // Pulls a one-eighty using the gyro, doesn't need to be precise
    /*public void OneEighty(double angle, double speed) {
        // Angle is counterclockwise (sorry)
        double normalizedHeading = normalize(getHeading());
        double normalizedAngle = normalize(angle);
        double angleDiff = normalizedHeading - normalizedAngle;

        angleDiff = (angleDiff / 180) * Math.PI;
        double c = sin(angleDiff);
        if (c >= 0) {
            // CW
            rw.frontLeft.setPower(speed);
            rw.frontRight.setPower(-speed);
        }
        else {
            // CCW
            rw.frontLeft.setPower(-speed);
            rw.frontRight.setPower(speed);
        }
        while (true) {
            double angle1 = normalize(angle + turnThreshold);
            double angle2 = normalize(angle - turnThreshold);
            double target = normalize(getHeading());
            double diff = normalize(angle2 - angle1);
            if (diff > 180) {
                double temp = angle1;
                angle1 = angle2;
                angle2 = temp;
            }
            boolean within = false;
            if (angle1 <= angle2) {
                within = target >= angle1 && target <= angle2;
            }

            else {
                within = target >= angle1 || target <= angle2;
            }

            if (within) {
                stop();
                break;
            }
        }
    }*/


    //endregion
    public void MoveCardinal(Direction direction, float speed) {
        switch (direction) {
            case FORWARD:
                rw.frontRight.setPower(speed);
                rw.frontLeft.setPower(speed);
                rw.backRight.setPower(speed);
                rw.backLeft.setPower(speed);
                break;
            case BACKWARD:
                rw.frontRight.setPower(-speed);
                rw.frontLeft.setPower(-speed);
                rw.backRight.setPower(-speed);
                rw.backLeft.setPower(-speed);
                break;
            case LEFT:
                rw.frontRight.setPower(speed);
                rw.frontLeft.setPower(-speed);
                rw.backRight.setPower(-speed);
                rw.backLeft.setPower(speed);
                break;
            case RIGHT:
                rw.frontRight.setPower(-speed);
                rw.frontLeft.setPower(speed);
                rw.backRight.setPower(speed);
                rw.backLeft.setPower(-speed);
                break;

        }
    }

    // Turns robot
    public void Rotate(boolean clockwise, double speed) {
        if (!clockwise) {
            setPower(rw.frontRight, speed);
            setPower(rw.frontLeft, -speed);
            setPower(rw.backRight, speed);
            setPower(rw.backLeft, -speed);
        }
        else {
            setPower(rw.frontRight, -speed);
            setPower(rw.frontLeft, speed);
            setPower(rw.backRight, -speed);
            setPower(rw.backLeft, speed);
        }
    }

    public void EncoderTurn(double speed, double counts, boolean clockwise) {
        int backLeftStart = rw.backLeft.getCurrentPosition();
        int backRightStart = rw.backRight.getCurrentPosition();
        int frontLeftStart = rw.frontLeft.getCurrentPosition();
        int frontRightStart = rw.frontRight.getCurrentPosition();

        if (runningOpMode == null) {
            return;
        }
        Rotate(clockwise, speed);
        while (runningOpMode.opModeIsActive()) {
            int backLeft = rw.backLeft.getCurrentPosition();
            int backRight = rw.backRight.getCurrentPosition();
            int frontLeft = rw.frontLeft.getCurrentPosition();
            int frontRight = rw.frontRight.getCurrentPosition();

            int backLeftDiff = Math.abs(backLeft - backLeftStart);
            int backRightDiff = Math.abs(backRight - backRightStart);
            int frontLeftDiff = Math.abs(frontLeft - frontLeftStart);
            int frontRightDiff = Math.abs(frontRight - frontRightStart);

            double avg = (backLeftDiff + backRightDiff + frontLeftDiff + frontRightDiff) / 4;

            if (runningOpMode != null) {
                runningOpMode.telemetry.addData("Average", avg);
                runningOpMode.telemetry.addData("Target", counts);
                runningOpMode.telemetry.update();
            }

            if (avg >= counts) {
                break;
            }
        }
        stop();
    }
    // Primary movement methods

    /**
     *
     * @param speed The speed of the robot from -1 to 1
     * @param angle Angle (in radians) that the robot should go
     * @param turn Turning velocity
     */
    public void MoveAngle(double speed, double angle, double turn) {
        double vRot = turn;

        double desiredAngle = (angle) + Math.PI / 4;
        if (desiredAngle < 0) {
            desiredAngle = desiredAngle + 2 * Math.PI;
        }
        if (desiredAngle >= 2 * Math.PI) {
            desiredAngle = desiredAngle % (2 * Math.PI);
        }

        double intermediateSin = sin(desiredAngle);
        double intermediateCos = cos(desiredAngle);

        double leftfront = speed * (intermediateSin) + (vRot * rotSpeed / speedMultiplier);
        double leftBackward = speed * (intermediateCos) + (vRot * rotSpeed / speedMultiplier);
        double rightfront = speed * (intermediateCos) - (vRot * rotSpeed / speedMultiplier);
        double rightBackward = speed * (intermediateSin) - (vRot * rotSpeed / speedMultiplier);

        if (Math.abs(rightBackward) < speedThreshold) {
            rightBackward = 0;
        }
        if (Math.abs(rightfront) < speedThreshold) {
            rightfront = 0;
        }
        if (Math.abs(leftBackward) < speedThreshold) {
            leftBackward = 0;
        }
        if (Math.abs(leftfront) < speedThreshold) {
            leftfront = 0;
        }
        setPower(rw.frontRight, rightfront);
        setPower(rw.frontLeft, leftfront);
        setPower(rw.backRight, rightBackward * 1.1);
        setPower(rw.backLeft, leftBackward);
    }

    public void setPower(DcMotor motor, double speed) {
        motor.setPower((speed * speedMultiplier));
    }

    // Ceases all movement
    public void stop() {
        rw.frontRight.setPower(0);
        rw.frontLeft.setPower(0);
        rw.backRight.setPower(0);
        rw.backLeft.setPower(0);
    }

    public void setPidCoefficients(double kp, double ki, double kd) {
        rw.backLeft.setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDCoefficients(kp, ki, kd));
        rw.backRight.setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDCoefficients(kp, ki, kd));
        rw.frontLeft.setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDCoefficients(kp, ki, kd));
        rw.frontRight.setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDCoefficients(kp, ki, kd));

    }

    // Blanket sets all zero power behaviours for the entire drivetrain
    public void setMotorZeroPower(DcMotor.ZeroPowerBehavior zeroPower) {
        rw.frontRight.setZeroPowerBehavior(zeroPower);
        rw.frontLeft.setZeroPowerBehavior(zeroPower);
        rw.backRight.setZeroPowerBehavior(zeroPower);
        rw.backLeft.setZeroPowerBehavior(zeroPower);
    }

    public void setMotorMode(DcMotor.RunMode runMode) {
        rw.frontRight.setMode(runMode);
        rw.frontLeft.setMode(runMode);
        rw.backRight.setMode(runMode);
        rw.backLeft.setMode(runMode);
    }

    // Sets the "overall" speed of the drivetrain
    public void setSpeedMultiplier(double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }
    
    public void displayInformation() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("BL", rw.backLeft.getVelocity(AngleUnit.DEGREES));
        packet.put("BR", rw.backRight.getVelocity(AngleUnit.DEGREES));
        packet.put("FL", rw.frontLeft.getVelocity(AngleUnit.DEGREES));
        packet.put("FR", rw.frontRight.getVelocity(AngleUnit.DEGREES));
        dashboard.sendTelemetryPacket(packet);
    }

    double normalize(double angle) {
        angle = (360 + angle % 360) % 360;
        return angle;
    }

}