/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */

public final class Constants {
    //can ids for the talons which power the drive motors
    public static final int[] driveTalons = {0, 1, 2, 3};
    public static final int[] encoderTalons = {1, 3}; // left, right
    public static final int[] bareTalons = {0, 2};  // left, right

    public static final int collectorMotorPort = 1;

    public static final double wheelCircumference = 18.5;
    public static final double gearboxRatio = 5.95;

    public static final double cameraHeight = 8.0; // height of camera from the ground
    public static final double cameraFovX = 49.7;
    public static final double cameraFovY = 59.6;
}