/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class Drive extends CommandBase {
  private final DriveTrain choochoo;

  private final DoubleSupplier forwards;
  private final DoubleSupplier turn;
  private final BooleanSupplier forwButt;

  public Drive(DriveTrain d, DoubleSupplier f, DoubleSupplier t, BooleanSupplier f2) {
    choochoo = d;
    forwards = f;
    turn = t;
    forwButt = f2;
    addRequirements(choochoo);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    choochoo.driveArcade((forwButt.getAsBoolean() ? -.45 : forwards.getAsDouble()), turn.getAsDouble());
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
