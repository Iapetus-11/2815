from wpilib.command import Command


class Drive(Command):
    def __init__(self, robot):
        super().__init__(self.__class__.__name__)

        self.robot = robot

        self.requires(self.get_robot().drive_train)

    def execute(self):
        self.robot.drive_train.drive(
            self.robot.xbox_controller.getY(0),  # left stick, y axis
            self.robot.xbox_controller.getX(1)  # right stick, x axis
        )
