from wpilib.command import Command


class Drive(Command):
    def __init__(self):
        super().__init__(self.__class__.__name__)

    def execute(self):
        raise NotImplementedError
