import wpilib
import ctre  # Not automatically installed, see here: https://robotpy.readthedocs.io/projects/ctre/en/stable/api.html

from commands.drive import Drive
import constants


class DriveTrain(wpilib.command.Subsystem):
    def __init__(self, robot):
        super().__init__(self.__class__.__name__)

        self.robot = robot

        self.talons = tuple(ctre.TalonSRX(i) for i in constants.drive_talon_ports)

        self.left_side = wpilib.SpeedControllerGroup(*self.talons[0:2])
        self.right_side = wpilib.SpeedControllerGroup(*self.talons[2:4])

        self.drivetrain = wpilib.drive.DifferentialDrive(self.left_side, self.right_side)

    def drive(speed: float, rotation: float):
        self.drivetrain.arcadeDrive(speed, rotation)

    def initDefaultCommand(self):
        self.setDefaultCommand(Drive(self.robot))
