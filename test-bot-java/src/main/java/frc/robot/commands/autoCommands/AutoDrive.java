/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AutoDrive extends CommandBase {
  private final DriveTrain choochoo;

  private final double power;
  private final double turn;
  private final double time;

  private Timer timer = new Timer();
  private boolean done = false;

  /**
   * Creates a new DriveStraight.
   */
  public AutoDrive(DriveTrain d, double p, double t, double i) {
    // Use addRequirements() here to declare subsystem dependencies.
    choochoo = d;
    power = p;
    turn = t;
    time = i;

    addRequirements(choochoo);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (timer.get() < time) {
      choochoo.drive(power, turn);
    } else {
      done = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
    choochoo.drive(0, 0);
    choochoo.resetEncoders();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
