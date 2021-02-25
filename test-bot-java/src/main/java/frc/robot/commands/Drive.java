/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class Drive extends CommandBase {
  private final DriveTrain choochoo;

  private final DoubleSupplier forwards;
  private final DoubleSupplier turn;
  private final BooleanSupplier alignButt;
  private final BooleanSupplier resetButt;
  
  private final NetworkTable limeLightTable = NetworkTableInstance.getDefault().getTable("limelight");
  private final NetworkTableEntry tx = limeLightTable.getEntry("tx");
  private final double kp = 0.02;

  public Drive(DriveTrain d, DoubleSupplier f, DoubleSupplier t, BooleanSupplier a, BooleanSupplier r) {
    choochoo = d;
    forwards = f;
    turn = t;
    alignButt = a;
    resetButt = r;

    addRequirements(choochoo);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (resetButt.getAsBoolean()) {
      System.out.println("encoder reset");
      choochoo.resetEncoders();
    }
    
    if (alignButt.getAsBoolean()) {
      double headingError = tx.getDouble(0.0);
      double steeringAdjust = kp * headingError;

      System.out.println(steeringAdjust);
      choochoo.drive(forwards.getAsDouble(), steeringAdjust);
    } else {
      double turnValue = turn.getAsDouble();
      double forwardsValue = -forwards.getAsDouble();

      if (Math.abs(turnValue) < .04) {
        turnValue = 0.0;
      }

      if (Math.abs(forwardsValue) < .03) {
        forwardsValue = 0.0;
      }

      choochoo.drive(forwardsValue, turnValue);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
