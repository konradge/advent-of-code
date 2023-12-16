package Days;

import java.util.Arrays;
import java.util.Objects;

public class Day13 implements Day {
    Layer[] layers;

    @Override
    public void init(String input) {
        String[] lines = input.split("\\n");
        int lastDepth = Integer.parseInt(lines[lines.length - 1].split(":")[0]);
        layers = new Layer[lastDepth + 1];
        for (int i = 0; i < lines.length; i++) {
            int[] line = Arrays.stream(lines[i].split(": ")).mapToInt(Integer::parseInt).toArray();
            layers[line[0]] = new Layer(line[1], line[0]);
        }
    }

    @Override
    public String part1() {
        return run() + "";
    }

    public int run() {
        int position = 0;
        int severity = 0;
        while (position < layers.length) {
            Layer currentLayer = layers[position];
            if (currentLayer != null) {
                severity += currentLayer.collide();
            }
            position++;
            Arrays.stream(layers).filter(Objects::nonNull).forEach(Layer::move);
        }
        return severity;
    }

    @Override
    public String part2() {
        int severity, i = 0;
        do {
            int finalI = i;
            Arrays.stream(layers).filter(Objects::nonNull).forEach(l -> {
                l.reset();
                l.move(finalI);
            });
            if (layers[0].position != 0) {
                severity = run();
            } else {
                severity = -1;
            }
            i++;
            if (i % 10000 == 0)
                System.out.println(i);
        } while (severity != 0);
        return "" + (i - 1);
    }
}

class Layer {
    int range, depth, position;
    Direction direction;

    private enum Direction {
        UP, DOWN;
    }

    public Layer(int range, int depth) {
        this.range = range;
        this.depth = depth;
        reset();
    }

    public void reset() {
        this.position = 0;
        this.direction = Direction.DOWN;
    }

    public void move() {
        this.move(1);
    }

    public void move(int steps) {
        int realSteps = steps % (this.range * 2 - 2);
        for (int i = 0; i < realSteps; i++) {
            if (this.direction == Direction.DOWN) {
                this.position++;
                if (this.position >= range) {
                    this.position -= 2;
                    this.direction = Direction.UP;
                }
            } else {
                this.position--;
                if (this.position < 0) {
                    this.position += 2;
                    this.direction = Direction.DOWN;
                }
            }
        }
    }

    public int collide() {
        if (this.position == 0) {
            return this.depth * this.range;
        } else {
            return 0;
        }
    }
}
