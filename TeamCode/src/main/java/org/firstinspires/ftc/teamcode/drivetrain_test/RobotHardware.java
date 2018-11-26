package org.firstinspires.ftc.teamcode.drivetrain_test;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotHardware {
        public DcMotorEx backLeft, backRight, frontLeft, frontRight, linearSlider, firstJoint, secondJoint;
        public RobotHardware(HardwareMap hwMap) {
            backLeft = (DcMotorEx)hwMap.get(DcMotor.class, "backLeft");
            backRight = (DcMotorEx)hwMap.get(DcMotor.class, "backRight");
            frontLeft = (DcMotorEx)hwMap.get(DcMotor.class,"frontLeft");
            frontRight = (DcMotorEx)hwMap.get(DcMotor.class,"frontRight");
            linearSlider = (DcMotorEx)hwMap.get(DcMotor.class, "linearSlider");
            firstJoint = (DcMotorEx)hwMap.get(DcMotor.class, "firstJoint");
            secondJoint = (DcMotorEx)hwMap.get(DcMotor.class, "secondJoint");
        }
    }