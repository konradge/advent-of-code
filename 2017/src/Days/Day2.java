package Days;

import java.util.Arrays;

public class Day2 implements Day {
    String[] input;

    @Override
    public void init(String input) {
        this.input = input.replaceAll("\\t", " ").split("\\n");
    }

    @Override
    public String part1() {
        int sum = 0;
        for (String row : input) {
            var ints = Arrays.stream(row.split(" ")).mapToInt(Integer::parseInt).toArray();

            sum += (Arrays.stream(ints).max().getAsInt() - Arrays.stream(ints).min().getAsInt());
        }
        return "" + sum;
    }

    @Override
    public String part2() {
        int sum = 0;
        rows:
        for (String row : input) {
            var ints = Arrays.stream(row.split(" ")).mapToInt(Integer::parseInt).toArray();

            for (int i = 0; i < ints.length - 1; i++) {
                for (int j = i + 1; j < ints.length; j++) {
                    int x = Math.max(ints[i], ints[j]);
                    int y = Math.min(ints[i], ints[j]);
                    if (Math.floorDiv(x, y) == (double) x / y) {
                        sum += (x / y);
                        continue rows;
                    }
                }
            }
        }
        return "" + sum;
    }
}
