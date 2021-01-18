from networktables import NetworkTables
import wpilib
import math


class Aligner(wpilib.command.Subsystem):
    def __init__(self, robot):
        super().__init__(self.__class__.__name__)

        self.robot = robot

        self.lime_light_table = NetworkTables.getTable('limelight')

        self.tx = self.lime_light_table.getEntry('tx')
        self.ty = self.lime_light_table.getEntry('ty')

    def periodic():
        ox = self.tx.getDouble(0)
        oy = self.ty.getDouble(0)

        nx = (1 / 160) * (ox - 159.5)
        ny = (1 / 120) * (119.5 - oy)

        vpw = 2 * math.tan(49.7 / 2)
        vph = 2 * math.tan(59.6 / 2)

        x = (vpw / 2) * nx
        y = (vph / 2) * ny

        height_of_camera = 8
        height_of_target = 24.5

        distance = ((height_of_target - height_of_camera) / math.tan(math.atan2(36, y)))

        print(f'Distance: {distance:7.2f} inches')
