/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  private final WPI_TalonSRX[] talons = new WPI_TalonSRX[4]; //4 talons for doing the driving

  private final DifferentialDrive tank;

  public DriveTrain() {
    for (int i = 0; i < talons.length; i++){
      talons[i] = new WPI_TalonSRX(Constants.driveTalons[i]);
    }

    //talons[3].setInverted(true); //use this if one of them needs to be inverted

    final SpeedControllerGroup left = new SpeedControllerGroup(talons[0], talons[1]);
    final SpeedControllerGroup right = new SpeedControllerGroup(talons[2], talons[3]);

    tank = new DifferentialDrive(left, right);
  }

  public void driveArcade(final double forw, final double turn) {
    //tank.arcadeDrive(forw, turn);
    tank.curvatureDrive(forw, turn, (forw < .15 && forw > -.15 ? true : false));
  }
}
