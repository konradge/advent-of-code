package Days;

import Utils.IO.Direction;

import java.util.ArrayList;

public class Day3 implements Day {
    int input;

    int currentStepSizeDirChanges = 0, currentStepSize = 1, stepsInDir = 0;
    Direction dir = Direction.RIGHT;
    int x = 1, y = 0;

    @Override
    public void init(String input) {
        this.input = Integer.parseInt(input.replaceAll("\\n", ""));
    }

    @Override
    public String part1() {
        ArrayList<Position> positions = new ArrayList<>(input);
        for (int i = 0; i < input; i++) {
            positions.add(new Position(i + 1, x, y));
            recalcPosition();
        }
        Position destination = positions.get(positions.size() - 1);
        return "" + (Math.abs(destination.x) + Math.abs(destination.y));
    }

    @Override
    public String part2() {
        ArrayList<Position> positions = new ArrayList<>(input);
        positions.add(new Position(1, 0, 0));
        x = 1;
        y = 0;
        dir = Direction.UP;
        currentStepSizeDirChanges = 1;
        currentStepSize = 1;
        stepsInDir = 1;
        while (true) {
            int finalX = x;
            int finalY = y;
            int value = positions.stream().filter(v -> Math.abs(v.x - finalX) <= 1 && Math.abs(v.y - finalY) <= 1).map(p -> p.value).reduce(0, Integer::sum);
            if (value > input) {
                return value + "";
            }
            positions.add(new Position(value, x, y));
            recalcPosition();
        }
    }

    void recalcPosition() {
        switch (dir) {
            case RIGHT -> x++;
            case LEFT -> x--;
            case UP -> y++;
            case DOWN -> y--;
        }
        stepsInDir++;
        if (stepsInDir >= currentStepSize) {
            stepsInDir = 0;
            currentStepSizeDirChanges++;
            dir = Direction.change(dir);
        }
        if (currentStepSizeDirChanges >= 2) {
            currentStepSize++;
            currentStepSizeDirChanges = 0;
        }
    }

    class Position {
        int value;
        int x;
        int y;

        public Position(int value, int x, int y) {
            this.value = value;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "value=" + value +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
