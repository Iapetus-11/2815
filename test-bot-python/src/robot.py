from commandbased import CommandBasedRobot
from wpilib.command import Command
import wpilib

from subsystems.drive_train import DriveTrain
import constants


class TestRobot(CommandBasedRobot):
    def robotInit(self):
        self.xbox_controller = XboxController(constants.xbox_controller_port)
        self.drive_train = DriveTrain(self)

    def autonomousInit(self):
        # self.autonomousProgram.start()
        pass


if __name__ == '__main__':
    wpilib.run(TestRobot)
