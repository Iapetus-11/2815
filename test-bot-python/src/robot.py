from commandbased import CommandBasedRobot
import wpilib

from subsystems.drive_train import DriveTrain
from subsystems.aligner import Aligner
import constants


class TestRobot(CommandBasedRobot):
    def robotInit(self):
        self.xbox_controller = XboxController(constants.xbox_controller_port)

        self.drive_train = DriveTrain(self)
        self.aligner = Aligner(self)

    def autonomousInit(self):
        # self.autonomousProgram.start()
        pass


if __name__ == '__main__':
    wpilib.run(TestRobot)
