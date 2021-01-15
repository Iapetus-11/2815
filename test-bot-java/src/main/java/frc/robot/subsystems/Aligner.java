/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.networktables.NetworkTable;

import java.lang.Math;

public class Aligner extends SubsystemBase {
  private final NetworkTable limeLightTable = NetworkTableInstance.getDefault().getTable("limelight");
  private final NetworkTableEntry tx = limeLightTable.getEntry("tx");
  private final NetworkTableEntry ty = limeLightTable.getEntry("ty");
  private final NetworkTableEntry ta = limeLightTable.getEntry("ta");

  public Aligner() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double ox = tx.getDouble(0.0);
    double oy = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);

    double nx = (1.0 / 160.0) * (ox - 159.5);
    double ny = (1.0 / 120.0) * (119.5 - oy);

    double vpw = 2.0 * Math.tan(49.7 / 2.0);
    double vph = 2.0 * Math.tan(59.6 / 2.0);

    double x = (vpw / 2.0) * nx;
    double y = (vph / 2.0) * ny;

    // double originalDist = 36.0;
    // double a2 = 5; // somewhere between 1 and 10 degrees
    // double a1 = -4.570237720903312; // based on a2

    double heightOfCamera = 8.0;
    double heightOfTarget = 24.5;

    // Math.tan(Math.atan2(1, y)
    double distance = ((heightOfTarget - heightOfCamera) / Math.tan(Math.atan2(36, y)));
    
    System.out.println(distance);

    // System.out.print(x);
    // System.out.println(y);
    // System.out.println(area);

    //System.out.println(String.format("X:%3.4f Y:%3.4f D:%3.4f", x, y, distance));
  }
}
