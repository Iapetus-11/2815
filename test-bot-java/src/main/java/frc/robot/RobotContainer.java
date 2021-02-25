/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.Collect;
import frc.robot.commands.Drive;
import frc.robot.commands.autoCommandGroups.TestAuto;
// import frc.robot.subsystems.Aligner;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.DriveTrain;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final ADXRS450_Gyro gyro = new ADXRS450_Gyro();

  private final DriveTrain choochoo = new DriveTrain(gyro);
  private final Collector collector = new Collector();
  // private final Aligner aligner = new Aligner();

  XboxController xbox = new XboxController(0);

  private final Drive drive = new Drive(choochoo, () -> xbox.getRawAxis(1), () -> xbox.getRawAxis(4), () -> xbox.getAButton(), () -> xbox.getYButton());
  private final Collect collect = new Collect(collector, () -> xbox.getXButton());
  private final TestAuto autoTest = new TestAuto(choochoo);
  
  public RobotContainer() {
    choochoo.setDefaultCommand(drive);
    collector.setDefaultCommand(collect);

    configureButtonBindings();
  }

  private void configureButtonBindings() {
  }

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autoTest;
  }
}