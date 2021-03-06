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

public class DriveTrain extends SubsystemBase {
  private final WPI_TalonSRX[] talons = new WPI_TalonSRX[4]; // 4 talons for doing the driving
  private final Faults leftFault = new Faults();
  private final Faults rightFault = new Faults();

  private final ADXRS450_Gyro gyro;

  private final DifferentialDrive tank;
  
  private double I = 30; // 30 on test bot

  public DriveTrain(ADXRS450_Gyro g) {
    gyro = g;

    for (int i = 0; i < talons.length; i++) {
      talons[i] = new WPI_TalonSRX(Constants.driveTalons[i]);
      talons[i].configFactoryDefault();
      talons[i].setSensorPhase(true);
      talons[i].setSelectedSensorPosition(0);
    }

    talons[Constants.encoderTalons[0]].setInverted(false);
    talons[Constants.encoderTalons[1]].setInverted(false); // CHANGE true on test bot, false here bc electrical did a dum


    talons[Constants.bareTalons[0]].follow(talons[Constants.encoderTalons[0]]);
    talons[Constants.bareTalons[0]].setInverted(InvertType.FollowMaster);
    talons[Constants.bareTalons[1]].follow(talons[Constants.encoderTalons[1]]);
    talons[Constants.bareTalons[1]].setInverted(InvertType.FollowMaster);

    // talons 1 and 3 have the encoders on them so we're going to use them
    // talons 0-1 are on the left side and talons 2-3 are on the right iirc
    tank = new DifferentialDrive(talons[Constants.encoderTalons[0]], talons[Constants.encoderTalons[1]]);

    tank.setRightSideInverted(false);
  }

  public void drive(double forw, double turn) {
    double absForw = Math.abs(forw);

    double error = -gyro.getRate() / I * (absForw < .65 ? absForw : .65);
    SmartDashboard.putNumber("Error", error);

    double eLeft = talons[Constants.encoderTalons[0]].getSelectedSensorVelocity();
    double eRight = talons[Constants.encoderTalons[1]].getSelectedSensorVelocity();

    double eForw = eLeft + eRight;

    SmartDashboard.putNumber("eForw", eForw);

    if (turn == 0 && forw != 0) {
      turn = error;
    }

    SmartDashboard.putNumber("Forw", forw);
    SmartDashboard.putNumber("Turn", turn);

    tank.curvatureDrive(forw, turn, true); // forw < .15 && forw > -.15

    // Noice, ctre does pid control for us!
    talons[Constants.encoderTalons[0]].getFaults(leftFault);
    talons[Constants.encoderTalons[1]].getFaults(rightFault);
  }

  public int getLeftETicks() {
    return talons[Constants.encoderTalons[0]].getSelectedSensorPosition();
  }

  public int getRightETicks() {
    return talons[Constants.encoderTalons[1]].getSelectedSensorPosition();
  }

  public double getAvgETicks() { // probably not going to be very useful because the encoders suck at being consistent
    return (getLeftETicks() + getRightETicks()) / 2.0;
  }

  public void resetEncoders() {
    talons[Constants.encoderTalons[0]].setSelectedSensorPosition(0);
    talons[Constants.encoderTalons[1]].setSelectedSensorPosition(0);
  }
}
