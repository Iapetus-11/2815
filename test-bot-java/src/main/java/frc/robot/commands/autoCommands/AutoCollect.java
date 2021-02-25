/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Collector;

public class AutoCollect extends CommandBase {
  private final Collector collector;

  private final double power;
  private final double time;

  private Timer timer = new Timer();
  private boolean done = false;

  /**
   * Creates a new AutoCollect.
   */
  public AutoCollect(Collector c, double p, double t) {
    collector = c;
    power = p;
    time = t;

    // Use addRequirements() here to declare subsystem dependencies
    addRequirements(collector); 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (timer.get() < time) {
      collector.collect(power);
    } else {
      done = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
    collector.collect(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
