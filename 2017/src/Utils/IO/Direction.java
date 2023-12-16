package Utils.IO;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;
    public static Direction change(Direction dir){
        return switch (dir){
            case RIGHT -> UP;
            case UP -> LEFT;
            case LEFT -> DOWN;
            case DOWN -> RIGHT;
        };
    }
}
