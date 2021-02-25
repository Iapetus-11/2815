/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class DriveTrainFullPID extends SubsystemBase {
  private final WPI_TalonSRX[] talons = new WPI_TalonSRX[4]; // 4 talons for doing the driving
  private final Faults leftFault = new Faults();
  private final Faults rightFault = new Faults();

  private final ADXRS450_Gyro gyro;
  private double P = .1; // .5
  private double I = .01; // .1
  private double D = 0;
  private double i = 0;
  // private double lastError = 0;

  private final DifferentialDrive tank;

  public DriveTrainFullPID(ADXRS450_Gyro g) {
    gyro = g;

    for (int i = 0; i < talons.length; i++) {
      talons[i] = new WPI_TalonSRX(Constants.driveTalons[i]);
      talons[i].configFactoryDefault();
      talons[i].setSensorPhase(true);
      talons[i].setSelectedSensorPosition(0);
    }

    talons[1].setInverted(false);
    talons[3].setInverted(true);


    talons[0].follow(talons[1]);
    talons[0].setInverted(InvertType.FollowMaster);
    talons[2].follow(talons[3]);
    talons[2].setInverted(InvertType.FollowMaster);

    // talons 1 and 3 have the encoders on them so we're going to use them
    // talons 0-1 are on the left side and talons 2-3 are on the right iirc
    tank = new DifferentialDrive(talons[1], talons[3]);

    tank.setRightSideInverted(false);
  }

  public void drive(double forw, double turn) {
    double eLeft = talons[1].getSelectedSensorPosition();
    double eRight = talons[3].getSelectedSensorPosition();

    SmartDashboard.putNumber("eLeft", eLeft);
    SmartDashboard.putNumber("eRight", eRight);

    double p = -gyro.getAngle();

    SmartDashboard.putNumber("error", p);

    if (turn == 0 && forw != 0) {  //  && (Math.abs(eLeft + eRight) > 50)
      i += p;
      // double d = p - lastError;
      // lastError = p;

      SmartDashboard.putString("PID", String.format("%.2f, %.2f, %.2f", P, I, D));
 
      turn = (P * p + I * i /*D * d*/);
    } else {
      gyro.reset();
      resetEncoders();
      i = 0;
    }

    SmartDashboard.putNumber("Forw", forw);
    SmartDashboard.putNumber("Turn", turn);

    tank.curvatureDrive(forw, turn, true); // forw < .15 && forw > -.15

    talons[1].getFaults(leftFault);
    talons[3].getFaults(rightFault);
  }

  public int getLeftETicks() {
    return talons[1].getSelectedSensorPosition();
  }

  public int getRightETicks() {
    return talons[3].getSelectedSensorPosition();
  }

  public void resetEncoders() {
    talons[1].setSelectedSensorPosition(0);
    talons[3].setSelectedSensorPosition(0);
  }
}
