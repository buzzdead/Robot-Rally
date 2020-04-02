package roborally.utilities.enums;

public enum TileName {
    // Walls
    WALL_RIGHT(23),
    WALL_BOTTOM(29),
    WALL_LEFT(30),
    WALL_TOP(31),
    // Walls with corners
    WALL_CORNER_TOP_RIGHT(16),
    WALL_CORNER_TOP_LEFT(24),
    WALL_CORNER_BOTTOM_LEFT(32),
    WALL_CORNER_BOTTOM_RIGHT(8),
    // Walls with cannons
    WALL_CANNON_TOP(45),
    WALL_CANNON_RIGHT(46),
    WALL_CANNON_BOTTOM(37),
    WALL_CANNON_LEFT(38),
    WALL_CANNON_DOUBLE_BOTTOM(87),
    WALL_CANNON_DOUBLE_LEFT(93),
    WALL_CANNON_DOUBLE_TOP(94),
    WALL_CANNON_DOUBLE_RIGHT(95),
    // Conveyor express belts
    CONVEYOR_EXPRESS_TO_NORTH(13),
    CONVEYOR_EXPRESS_TO_EAST(14),
    CONVEYOR_EXPRESS_TO_SOUTH(21),
    CONVEYOR_EXPRESS_TO_WEST(22),
    CONVEYOR_EXPRESS_ROTATE_COUNTER_CLOCKWISE_WEST_TO_SOUTH(17),
    CONVEYOR_EXPRESS_ROTATE_COUNTER_CLOCKWISE_NORTH_TO_WEST(18),
    CONVEYOR_EXPRESS_ROTATE_CLOCKWISE_NORTH_TO_EAST(19),
    CONVEYOR_EXPRESS_ROTATE_CLOCKWISE_EAST_TO_SOUTH(20),
    CONVEYOR_EXPRESS_ROTATE_COUNTER_CLOCKWISE_SOUTH_TO_EAST(25),
    CONVEYOR_EXPRESS_ROTATE_COUNTER_CLOCKWISE_EAST_TO_NORTH(26),
    CONVEYOR_EXPRESS_ROTATE_CLOCKWISE_WEST_TO_NORTH(27),
    CONVEYOR_EXPRESS_ROTATE_CLOCKWISE_SOUTH_TO_WEST(28),
    CONVEYOR_EXPRESS_JOIN_NORTH_FROM_SOUTH_AND_WEST(73),
    CONVEYOR_EXPRESS_JOIN_EAST_FROM_NORTH_AND_WEST(74),
    CONVEYOR_EXPRESS_JOIN_SOUTH_FROM_NORTH_AND_EAST(75),
    CONVEYOR_EXPRESS_JOIN_WEST_FROM_SOUTH_AND_EAST(76),
    CONVEYOR_EXPRESS_JOIN_NORTH_FROM_SOUTH_AND_EAST(77),
    CONVEYOR_EXPRESS_JOIN_EAST_FROM_SOUTH_AND_WEST(78),
    CONVEYOR_EXPRESS_JOIN_EAST_FROM_NORTH_AND_SOUTH(81),
    CONVEYOR_EXPRESS_JOIN_SOUTH_FROM_EAST_AND_WEST(82),
    CONVEYOR_EXPRESS_JOIN_WEST_FROM_NORTH_AND_SOUTH(83),
    CONVEYOR_EXPRESS_JOIN_NORTH_FROM_EAST_AND_WEST(84),
    CONVEYOR_EXPRESS_JOIN_WEST_FROM_NORTH_AND_EAST(85),
    CONVEYOR_EXPRESS_JOIN_SOUTH_FROM_NORTH_AND_WEST(86),
    // Conveyor belts
    CONVEYOR_TO_NORTH(49),
    CONVEYOR_TO_SOUTH(50),
    CONVEYOR_TO_WEST(51),
    CONVEYOR_TO_EAST(52),
    CONVEYOR_ROTATE_COUNTER_CLOCKWISE_WEST_TO_SOUTH(33),
    CONVEYOR_ROTATE_COUNTER_CLOCKWISE_NORTH_TO_WEST(34),
    CONVEYOR_ROTATE_CLOCKWISE_NORTH_TO_EAST(35),
    CONVEYOR_ROTATE_CLOCKWISE_EAST_TO_SOUTH(36),
    CONVEYOR_ROTATE_COUNTER_CLOCKWISE_SOUTH_TO_EAST(41),
    CONVEYOR_ROTATE_COUNTER_CLOCKWISE_EAST_TO_NORTH(42),
    CONVEYOR_ROTATE_CLOCKWISE_WEST_TO_NORTH(43),
    CONVEYOR_ROTATE_CLOCKWISE_SOUTH_TO_WEST(44),
    CONVEYOR_JOIN_NORTH_FROM_SOUTH_AND_WEST(57),
    CONVEYOR_JOIN_EAST_FROM_NORTH_AND_WEST(58),
    CONVEYOR_JOIN_SOUTH_FROM_NORTH_AND_EAST(59),
    CONVEYOR_JOIN_WEST_FROM_SOUTH_AND_EAST(60),
    CONVEYOR_JOIN_EAST_FROM_NORTH_AND_SOUTH(61),
    CONVEYOR_JOIN_SOUTH_FROM_EAST_AND_WEST(62),
    CONVEYOR_JOIN_NORTH_FROM_SOUTH_AND_EAST(65),
    CONVEYOR_JOIN_EAST_FROM_SOUTH_AND_WEST(66),
    CONVEYOR_JOIN_SOUTH_FROM_NORTH_AND_WEST(67),
    CONVEYOR_JOIN_WEST_FROM_NORTH_AND_EAST(68),
    CONVEYOR_JOIN_NORTH_FROM_EAST_AND_WEST(69),
    CONVEYOR_JOIN_WEST_FROM_NORTH_AND_SOUTH(70),
    // Lasers
    LASER_HORIZONTAL(39),
    LASER_CROSS(40),
    LASER_VERTICAL(47),
    // Double lasers
    LASER_DOUBLE_CROSS(101),
    LASER_DOUBLE_VERTICAL(102),
    LASER_DOUBLE_HORIZONTAL(103),
    // Standard floor tiles
    FLOOR_1(4),
    FLOOR_2(5),
    BLACK_VOID(91),
    HOLE(6),
    // Repair and stuff
    WRENCH_HAMMER(7),
    WRENCH(15),
    // Cogs
    COG_COUNTER_CLOCKWISE(53),
    COG_CLOCKWISE(54),
    // Flags
    FLAG_1(55),
    FLAG_2(63),
    FLAG_3(71),
    FLAG_4(79),
    // Start positions
    START_POSITION_1(121),
    START_POSITION_2(122),
    START_POSITION_3(123),
    START_POSITION_4(124),
    START_POSITION_5(129),
    START_POSITION_6(130),
    START_POSITION_7(131),
    START_POSITION_8(132);

    private final int tileID;

    TileName(int tileID) {
        this.tileID = tileID;
        //TODO: Throw exception if two matching IDs
    }

    public int getTileID() {
        return this.tileID;
    }
}
