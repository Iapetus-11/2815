from wpilib.command import Command


class Drive(Command):
    def __init__(self):
        super().__init__(self.__class__.__name__)

        self.requires(self.getRobot().drive_train)

    def execute(self):
        self.getRobot().drive_train.drive(
            self.getRobot().xbox_controller.getY(0),  # left stick, y axis
            self.getRobot().xbox_controller.getX(1)  # right stick, x axis
        )
