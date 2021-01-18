import wpilib
import ctre

from commands.drive import Drive
import constants


class DriveTrain(wpilib.command.Subsystem):
    def __init__(self):
        super().__init__(self.__class__.__name__)

        self._talons = tuple(ctre.TalonSRX(i) for i in constants.drive_talon_ports)

        self._left_side = wpilib.SpeedControllerGroup(*self._talons[0:2])
        self._right_side = wpilib.SpeedControllerGroup(*self._talons[2:4])

        self._drive = wpilib.drive.DifferentialDrive(self._left_side, self._right_side)

    def drive(speed: float, rotation: float):
        self._drive.arcadeDrive(speed, rotation)

    def initDefaultCommand(self):
        self.setDefaultCommand(Drive())
