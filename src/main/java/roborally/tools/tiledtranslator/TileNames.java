package roborally.tools.tiledtranslator;

import java.security.PublicKey;

public enum TileNames {
    FLOOR(5),
    HOLE_ALTERNATIVE(6),
    WRENCH_HAMMER(7),
    WALL_CORNER_BOTTOM_RIGHT(8),
    CONVEYOR_EXPRESS_NORTH(13),
    CONVEYOR_EXPRESS_EAST(14),
    WRENCH(15),
    WALL_CORNER_TOP_RIGHT(16),
    CONVEYOR_EXPRESS_ROTATE_COUNTER_CLOCKWISE_WEST_TO_SOUTH(17),
    CONVEYOR_EXPRESS_ROTATE_COUNTER_CLOCKWISE_NORTH_TO_WEST(18),
    CONVEYOR_EXPRESS_ROTATE_CLOCKWISE_NORTH_TO_EAST(19),
    CONVEYOR_EXPRESS_ROTATE_CLOCKWISE_EAST_TO_SOUTH(20),
    CONVEYOR_EXPRESS_DOWN(21),
    CONVEYOR_EXPRESS_LEFT(22),
    WALL_RIGHT(23),
    WALL_CORNER_TOP_LEFT(24),
    CONVEYOR_EXPRESS_ROTATE_COUNTER_CLOCKWISE_SOUTH_TO_EAST(25),
    CONVEYOR_EXPRESS_ROTATE_COUNTER_CLOCKWISE_EAST_TO_NORTH(26),
    CONVEYOR_EXPRESS_ROTATE_CLOCKWISE_WEST_TO_NORTH(27),
    CONVEYOR_EXPRESS_ROTATE_CLOCKWISE_SOUTH_TO_WEST(28),
    WALL_BOTTOM(29),
    WALL_LEFT(30),
    WALL_TOP(31),
    WALL_CORNER_BOTTOM_LEFT(32),
    CONVEYOR_ROTATE_COUNTER_CLOCKWISE_WEST_TO_SOUTH(33),
    CONVEYOR_ROTATE_COUNTER_CLOCKWISE_NORTH_TO_WEST(34),
    CONVEYOR_ROTATE_CLOCKWISE_NORTH_TO_EAST(35),
    CONVEYOR_ROTATE_CLOCKWISE_EAST_TO_SOUTH(36),
    WALL_CANON_BOTTOM(37),
    WALL_CANON_LEFT(38),
    LASER_HORIZONTAL(39),
    LASER_CROSS(40),
    CONVEYOR_ROTATE_COUNTER_CLOCKWISE_SOUTH_TO_EAST(41),
    CONVEYOR_ROTATE_COUNTER_CLOCKWISE_EAST_TO_NORTH(42),
    CONVEYOR_ROTATE_CLOCKWISE_WEST_TO_NORTH(43),
    CONVEYOR_ROTATE_CLOCKWISE_SOUTH_TO_WEST(44),
    WALL_CANON_TOP(45),
    WALL_CANON_RIGHT(46),
    LASER_VERTICAL(47),
    CONVEYOR_NORTH(49),
    CONVEYOR_SOUTH(50),
    CONVEYOR_LEFT(51),
    CONVEYOR_RIGHT(52),
    COG_COUNTER_CLOCKWISE(53),
    COG_CLOCKWISE(54),
    FLAG_1(55),
    CONVEYOR_JOIN_NORTH_FROM_SOUTH_AND_WEST(57),
    CONVEYOR_JOIN_EAST_FROM_NORTH_AND_WEST(58),
    CONVEYOR_JOIN_SOUTH_FROM_NORTH_AND_EAST(59),
    CONVEYOR_JOIN_WEST_FROM_SOUTH_AND_EAST(60),
    CONVEYOR_JOIN_EAST_FROM_NORTH_AND_SOUTH(61),
    CONVEYOR_JOIN_SOUTH_FROM_EAST_AND_WEST(62),
    FLAG_2(63),
    CONVEYOR_JOIN_NORTH_FROM_SOUTH_AND_EAST(65),
    CONVEYOR_JOIN_EAST_FROM_SOUTH_AND_WEST(66),
    CONVEYOR_JOIN_SOUTH_FROM_NORTH_AND_WEST(67),
    CONVEYOR_JOIN_WEST_FROM_NORTH_AND_EAST(68),
    CONVEYOR_JOIN_NORTH_FROM_EAST_AND_WEST(69),
    CONVEYOR_JOIN_WEST_FROM_NORTH_AND_SOUTH(70),
    FLAG_3(71),
    CONVEYOR_EXPRESS_JOIN_NORTH_FROM_SOUTH_AND_WEST(73),
    CONVEYOR_EXPRESS_JOIN_EAST_FROM_NORTH_AND_WEST(74),
    CONVEYOR_EXPRESS_JOIN_SOUTH_FROM_NORTH_AND_EAST(75),
    CONVEYOR_EXPRESS_JOIN_WEST_FROM_SOUTH_AND_EAST(76),
    CONVEYOR_EXPRESS_JOIN_NORTH_FROM_SOUTH_AND_EAST(77),
    CONVEYOR_EXPRESS_JOIN_EAST_FROM_SOUTH_AND_WEST(78),
    FLAG_4(79),
    CONVEYOR_EXPRESS_JOIN_EAST_FROM_NORTH_AND_SOUTH(81),
    CONVEYOR_EXPRESS_JOIN_SOUTH_FROM_EAST_AND_WEST(82),
    CONVEYOR_EXPRESS_JOIN_WEST_FROM_NORTH_AND_SOUTH(83),
    CONVEYOR_EXPRESS_JOIN_NORTH_FROM_EAST_AND_WEST(84),
    CONVEYOR_EXPRESS_JOIN_WEST_FROM_NORTH_AND_EAST(85),
    CONVEYOR_EXPRESS_JOIN_SOUTH_FROM_NORTH_AND_WEST(86),
    WALL_CANON_DOUBLE_BOTTOM(87),
    HOLE(91),
    WALL_CANON_DOUBLE_LEFT(93),
    WALL_CANON_DOUBLE_TOP(94),
    WALL_CANON_DOUBLE_RIGHT(95),
    LASER_DOUBLE_CROSS(101),
    LASER_DOUBLE_VERTICAL(102),
    LASER_DOUBLE_HORIZONTAL(103),
    START_POSITION_1(121),
    START_POSITION_2(122),
    START_POSITION_3(123),
    START_POSITION_4(124),
    START_POSITION_5(129),
    START_POSITION_6(130),
    START_POSITION_7(131),
    START_POSITION_8(132);

    private final int tileID;

    TileNames(int tileID) {
        this.tileID = tileID;
        //TODO: Throw exception if two matching IDs
    }

    public int getTileID() {
        return this.tileID;
    }
}
