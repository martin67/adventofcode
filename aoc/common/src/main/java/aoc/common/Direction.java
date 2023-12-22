package aoc.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Direction {
    North, South, East, West, Up, Right, Down, Left, NorthEast, NorthWest, SouthEast, SouthWest, Unknown;

    public Direction opposite() {
        return switch (this) {
            case North -> Direction.South;
            case Up -> Direction.Down;
            case East -> Direction.West;
            case Right -> Direction.Left;
            case South -> Direction.North;
            case Down -> Direction.Up;
            case West -> Direction.East;
            case Left -> Direction.Right;
            case NorthEast -> Direction.SouthWest;
            case NorthWest -> Direction.SouthEast;
            case SouthEast -> Direction.NorthWest;
            case SouthWest -> Direction.NorthEast;
            default -> Direction.Unknown;
        };
    }

    public Direction bounceWall(Direction wall) {
        //Direction result;
        return switch (this) {
            case NorthEast -> {
                if (wall == Direction.North) {
                    yield Direction.SouthEast;
                } else if (wall == Direction.East) {
                    yield Direction.NorthWest;
                } else {
                    log.error("Bounce from northeast, no wall");
                    yield Direction.Unknown;
                }
            }
            case NorthWest -> {
                if (wall == Direction.North) {
                    yield Direction.SouthWest;
                } else if (wall == Direction.West) {
                    yield Direction.NorthEast;
                } else {
                    log.error("Bounce from northwest, no wall");
                    yield Direction.Unknown;
                }
            }
            case SouthEast -> {
                if (wall == Direction.South) {
                    yield Direction.NorthEast;
                } else if (wall == Direction.East) {
                    yield Direction.SouthWest;
                } else {
                    log.error("Bounce from southeast, no wall");
                    yield Direction.Unknown;
                }
            }
            case SouthWest -> {
                if (wall == Direction.South) {
                    yield Direction.NorthWest;
                } else if (wall == Direction.West) {
                    yield Direction.SouthEast;
                } else {
                    log.error("Bounce from southwest, no wall");
                    yield Direction.Unknown;
                }
            }
            default -> Direction.Unknown;
        };
    }

    public Direction turn(Direction newDirection) {
        return switch (this) {
            case North, Up -> switch (newDirection) {
                case East -> Right;
                case West -> Left;
                case Left -> West;
                case Right -> East;
                default -> {
                    log.error("Wrong turn");
                    yield Unknown;
                }
            };
            case East, Right -> switch (newDirection) {
                case South -> Right;
                case North -> Left;
                case Left -> North;
                case Right -> South;
                default -> {
                    log.error("Wrong turn");
                    yield Unknown;
                }
            };
            case South, Down -> switch (newDirection) {
                case West -> Right;
                case East -> Left;
                case Left -> East;
                case Right -> West;
                default -> {
                    log.error("Wrong turn");
                    yield Unknown;
                }
            };
            case West, Left -> switch (newDirection) {
                case North -> Right;
                case South -> Left;
                case Left -> South;
                case Right -> North;
                default -> {
                    log.error("Wrong turn");
                    yield Unknown;
                }
            };
            default -> {
                log.error("Illegal currentDirection");
                yield Unknown;
            }
        };
    }

    public Direction turn(Direction newDirection, int degrees) {
        return switch (this) {
            case North -> switch (newDirection) {
                case Left -> switch (degrees) {
                    case 90 -> West;
                    case 180 -> South;
                    case 270 -> East;
                    case 360 -> North;
                    default -> throw new IllegalStateException("Unexpected value: " + degrees);
                };
                case Right -> switch (degrees) {
                    case 90 -> East;
                    case 180 -> South;
                    case 270 -> West;
                    case 360 -> North;
                    default -> throw new IllegalStateException("Unexpected value: " + degrees);
                };
                default -> {
                    log.error("Wrong direction");
                    yield Unknown;
                }
            };
            case East -> switch (newDirection) {
                case Left -> switch (degrees) {
                    case 90 -> North;
                    case 180 -> West;
                    case 270 -> South;
                    case 360 -> East;
                    default -> throw new IllegalStateException("Unexpected value: " + degrees);
                };
                case Right -> switch (degrees) {
                    case 90 -> South;
                    case 180 -> West;
                    case 270 -> North;
                    case 360 -> East;
                    default -> throw new IllegalStateException("Unexpected value: " + degrees);
                };
                default -> {
                    log.error("Wrong direction");
                    yield Unknown;
                }
            };
            case South -> switch (newDirection) {
                case Left -> switch (degrees) {
                    case 90 -> East;
                    case 180 -> North;
                    case 270 -> West;
                    case 360 -> South;
                    default -> throw new IllegalStateException("Unexpected value: " + degrees);
                };
                case Right -> switch (degrees) {
                    case 90 -> West;
                    case 180 -> North;
                    case 270 -> East;
                    case 360 -> South;
                    default -> throw new IllegalStateException("Unexpected value: " + degrees);
                };
                default -> {
                    log.error("Wrong direction");
                    yield Unknown;
                }
            };
            case West -> switch (newDirection) {
                case Left -> switch (degrees) {
                    case 90 -> South;
                    case 180 -> East;
                    case 270 -> North;
                    case 360 -> West;
                    default -> throw new IllegalStateException("Unexpected value: " + degrees);
                };
                case Right -> switch (degrees) {
                    case 90 -> North;
                    case 180 -> East;
                    case 270 -> South;
                    case 360 -> West;
                    default -> throw new IllegalStateException("Unexpected value: " + degrees);
                };
                default -> {
                    log.error("Wrong direction");
                    yield Unknown;
                }
            };
            default -> {
                log.error("Illegal currentDirection");
                yield Unknown;
            }
        };
    }

    public String shortName() {
        return switch (this) {
            case North -> "N";
            case Up -> "U";
            case East -> "E";
            case Right -> "R";
            case South -> "S";
            case Down -> "D";
            case West -> "W";
            case Left -> "L";
            case NorthEast -> "NE";
            case NorthWest -> "NW";
            case SouthEast -> "SE";
            case SouthWest -> "SW";
            default -> "?";
        };
    }

    public static Direction fromString(String in) {
        for (var direction : values()) {
            if (direction.name().equals(in) || direction.shortName().equals(in)) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }
}
