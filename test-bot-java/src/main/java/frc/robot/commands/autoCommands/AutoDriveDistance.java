/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AutoDriveDistance extends CommandBase {
  private final DriveTrain choochoo;

  private final double eTicksToTravel;
  private final double power;

  private boolean done = false;

  /**
   * Creates a new AutoDriveDistance.
   * (drivetrain, wheel circumference (inches), gearbox ratio, distance inches, power)
   */
  public AutoDriveDistance(DriveTrain d, double c, double r, double i, double p) {
    choochoo = d;
    eTicksToTravel = (360 / c) * (i / r);
    power = p;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(choochoo);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    choochoo.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println(String.format("%.3f", (choochoo.getLeftETicks() + choochoo.getRightETicks())/2.0));

    if ((choochoo.getLeftETicks() + choochoo.getRightETicks())/2.0 < eTicksToTravel) {
      choochoo.drive(-power, 0);
    } else {
      done = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    choochoo.drive(0, 0);
    choochoo.resetEncoders();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
