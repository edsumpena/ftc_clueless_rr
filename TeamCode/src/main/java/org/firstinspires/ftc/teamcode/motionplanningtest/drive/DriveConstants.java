package org.firstinspires.ftc.teamcode.motionplanningtest.drive;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints;
import com.qualcomm.hardware.motors.NeveRest20Gearmotor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.teamcode.motionplanningtest.drive.opmode.Neverest20GearmotorConfig;

/*
 * Constants shared between multiple drive types.
 */
@Config
public class DriveConstants {

    private DriveConstants() {

    }

    /*
     * TODO: Tune or adjust the following constants to fit your robot. Note that the non-final
     * fields may also be edited through the dashboard (connect to the robot's WiFi network and
     * navigate to https://192.168.49.1:8080/dash). Make sure to save the values here after you
     * adjust them in the dashboard; **config variable changes don't persist between app restarts**.
     */
    public static final Neverest20GearmotorConfig MOTOR_CONFIG = new Neverest20GearmotorConfig();
    private static final double TICKS_PER_REV = MOTOR_CONFIG.getTicksPerRev();

    public static double WHEEL_RADIUS = 2; // in
    public static double GEAR_RATIO = 0.66666; // output/input
    public static double TRACK_WIDTH = 11; // in

    public static DriveConstraints BASE_CONSTRAINTS = new DriveConstraints(24, 12, Math.PI / 2, Math.PI / 2);

    public static double kV = 0;
    public static double kA = 0;
    public static double kStatic = 0;


    public static double encoderTicksToInches(int ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    public static double rpmToVelocity(double rpm) {
        return rpm * DriveConstants.GEAR_RATIO * 2 * Math.PI * DriveConstants.WHEEL_RADIUS / 60.0;
    }
}
