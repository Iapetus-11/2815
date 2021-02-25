/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autoCommands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class AutoDriveToTarget extends CommandBase {
  private final DriveTrain choochoo;
  
  private final NetworkTable limeLightTable = NetworkTableInstance.getDefault().getTable("limelight");
  private final NetworkTableEntry tx = limeLightTable.getEntry("tx");
  private final NetworkTableEntry ty = limeLightTable.getEntry("ty");

  private double initialTargetDistanceTicks;

  private Timer timer = new Timer(); // used to not accelerate at full speed right away
  private boolean done = false;

  /**
   * Creates a new AutoDriveToTarget.
   */
  public AutoDriveToTarget(DriveTrain d) {
    choochoo = d;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(choochoo);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initialTargetDistanceTicks = getTargetDistanceTicks();
    timer.start();
  }

  private double getTargetAngle() {
    return tx.getDouble(0.0);
  }

  private double getTargetDistanceTicks() {
    // double nx = (1.0 / 160.0) * (initialTargetAngle - 159.5);
    double ny = (1.0 / 120.0) * (119.5 - ty.getDouble(0.0));

    // double vpw = 2.0 * Math.tan(Constants.cameraFovX / 2.0);
    double vph = 2.0 * Math.tan(Constants.cameraFovY / 2.0);

    // double x = (vpw / 2.0) * nx;
    double y = (vph / 2.0) * ny;
    
    // target height is 0 because it's on the ground stupid
    double distance = ((0.0 - Constants.cameraHeight) / Math.tan(Math.atan2(36, y)));

    return (360 / Constants.wheelCircumference) * (distance / Constants.gearboxRatio);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double time = timer.get();
    double power = (time < 1 ? time / 2 : .5); // accelerate smoothly over the time of 1 sec to half power
    
    if (choochoo.getAvgETicks() < initialTargetDistanceTicks) {
      choochoo.drive(power, getTargetAngle());
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
