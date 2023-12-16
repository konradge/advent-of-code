package Days;

import java.util.Arrays;

public class Day5 implements Day {
    Machine m;

    @Override
    public void init(String input) {
        m = new Machine(Arrays.stream(input.split("\\n")).mapToInt(Integer::parseInt).toArray());
    }

    @Override
    public String part1() {
        return "" + m.run();
    }

    @Override
    public String part2() {
        m.reset();
        return "" + m.runStrange();
    }

    public class Machine {
        private final int[] originalInstructions;
        private int[] instructions;
        public int position = 0, steps = 0;

        public Machine(int[] instructions) {
            this.instructions = instructions;
            this.originalInstructions = instructions.clone();
        }

        public void reset() {
            position = 0;
            steps = 0;
            instructions = originalInstructions;
        }

        public int run() {
            while (position >= 0 && position < instructions.length) {
                position += (instructions[position]++);
                steps++;
            }
            return steps;
        }

        public int runStrange() {
            while (position >= 0 && position < instructions.length) {
                int offset = instructions[position];
                if (offset >= 3) {
                    instructions[position]--;
                } else {
                    instructions[position]++;
                }
                position += offset;
                steps++;
            }
            return steps;
        }
    }
}
