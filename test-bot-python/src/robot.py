from commandbased import CommandBasedRobot
from wpilib.command import Command
import wpilib

import constants


class TestRobot(CommandBasedRobot):
    def robotInit(self):
        Command.getRobot = (lambda x=None: self)

        self.xbox_controller = XboxController(constants.xbox_controller_port)

    def autonomousInit(self):
        # self.autonomousProgram.start()
        pass


if __name__ == '__main__':
    wpilib.run(TestRobot)
